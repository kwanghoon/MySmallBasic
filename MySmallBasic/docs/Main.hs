--------------------------------------------------------------------------
-- A Transformation of A Small Basic Program into `Continuation' blocks --
--   [ Updated: Octiber 1st, 2016]                                      --
--                                                                      --
--   Kwanghoon Choi                                                     --
--------------------------------------------------------------------------

module Main where

data Expr = 
    ExprArray      ArrayExpr
  | ExprArith      ArithExpr
  | ExprCond       CondExpr
  | ExprMethodCall MethodCallExpr
  | ExprParen      ParenExpr
  | ExprProperty   PropertyExpr
  | ExprVar        Var
  deriving Show

data ArrayExpr =
    ArrayExpr String [Expr]
  deriving Show

data ArithExpr =
    ArithExpr ArithOp Expr Expr
  deriving Show

data ArithOp = Plus | Minus | Multifly | Divide | Unary_Minus
  deriving Show

data CompExpr =
    CompExpr CompOp Expr Expr
  deriving Show

data CompOp = Less_Than | Less_Equal | Greater_Than | Greater_Equal | Equal | Not_Equal
  deriving Show

data CondExpr =
    CondExprLit Lit
  | CondExprLogical LogicalExpr
  | CondExprComp CompExpr
  deriving Show

data Lit = Lit String
  deriving Show

data LogicalExpr =
    LogicalExpr LogicalOp Expr Expr
  deriving Show

data LogicalOp = And | Or
  deriving Show

data MethodCallExpr = MethodCallExpr String String [Expr]
  deriving Show

data ParenExpr = ParenExpr Expr
  deriving Show

data PropertyExpr = PropertyExpr String String
  deriving Show

data Var = Var String
  deriving Show

data Stmt = 
    StmtAssign AssignStmt
  | StmtBlock  BlockStmt
  | StmtExpr   ExprStmt
  | StmtFor    ForStmt
  | StmtGoto   GotoStmt
  | StmtIf     IfStmt
  | StmtLabel  Label
  | StmtSubDef SubDef
  | StmtWhile  WhileStmt
  | StmtSubCall SubCallStmt
  deriving Show

data AssignStmt = AssignStmt Expr Expr
  deriving Show

data BlockStmt = BlockStmt [Stmt]
  deriving Show

data ExprStmt = ExprStmt Expr
  deriving Show

data ForStmt = ForStmt Var Expr Expr (Maybe Expr) Stmt
  deriving Show

data GotoStmt = GotoStmt String
  deriving Show

data IfStmt = IfStmt CondExpr Stmt (Maybe Stmt)
  deriving Show

data Label = Label String
  deriving Show

data SubDef = SubDef String Stmt
  deriving Show

data WhileStmt = WhileStmt CondExpr Stmt
  deriving Show

data SubCallStmt = SubCalLStmt String
  deriving Show

-----

type State = (Int, [(String,Stmt)])

data K a = K (State -> (a, State))

instance Functor K where
  fmap f (K g) = K (\i -> 
                    let (a, i') = g i
                    in  (f a, i'))

instance Applicative K where
  pure x = K (\i -> (x,i))
  (K f) <*> (K x) = K (\i -> let (ab, i') = f i 
                                 (a,  i'') = x i'
                             in  (ab a, i''))

instance Monad K where
  return x  = K (\i -> (x, i))
  (>>=) (K g) f = K (\i -> let (r,i') = g i 
                               K h    = f r
                           in  h i')

newLabel :: K String
newLabel = 
  K (\state -> 
    let (i, blkmap) = state
        state'      = (i+1,blkmap)
    in  ("$L" ++ show i, state'))

putBlock :: String -> Stmt -> K ()
putBlock label stmt =
  K (\state ->
    let (i, blkmap) = state
        state'      = (i, (label,stmt):blkmap)
    in  ((), state'))

kTransform :: Stmt -> Stmt -> K Stmt
kTransform (StmtWhile (WhileStmt cond body)) stmtk =
  do l     <- newLabel
     stmt  <- kTransform body (StmtGoto (GotoStmt l))
     putBlock l (StmtBlock 
                  (BlockStmt
                     [ StmtIf
                        ( IfStmt cond stmt (Just stmtk)) ]))
     return (StmtGoto (GotoStmt l))

kTransform (StmtFor (ForStmt v init to maybestep body)) stmtk =
  do linit <- newLabel
     ltest <- newLabel
     stmt' <- kTransform body (StmtBlock (BlockStmt []))
     let step = case maybestep of
                 Nothing  -> (ExprCond (CondExprLit (Lit "1")))
                 (Just s) -> s
     let var = ExprVar v
     putBlock linit
       (StmtBlock (BlockStmt 
         [ StmtAssign (AssignStmt var init)
         , StmtGoto (GotoStmt ltest) ]))
     putBlock ltest 
       (StmtBlock (BlockStmt
         [StmtIf (IfStmt (CondExprComp (CompExpr Less_Equal var to))
                   (StmtBlock (BlockStmt 
                     [ stmt'
                     , StmtAssign (AssignStmt 
                         var (ExprArith (ArithExpr Plus var step)))
                     , StmtGoto (GotoStmt ltest)]))
                   (Just stmtk))]))
     return (StmtGoto (GotoStmt linit))

kTransform (StmtIf (IfStmt cond stmt maybestmt)) stmtk = 
  do stmtthen <- kTransform stmt stmtk
     maybestmtelse <- kTransformMaybe maybestmt stmtk
     return (StmtIf (IfStmt cond stmtthen maybestmtelse))

kTransform (StmtBlock (BlockStmt [])) stmtk = 
  return (StmtBlock (BlockStmt [stmtk]))

kTransform (StmtBlock (BlockStmt (StmtGoto (GotoStmt l) : stmts))) stmtk =
  do _ <- kTransform (StmtBlock (BlockStmt stmts)) stmtk
     return (StmtBlock (BlockStmt [StmtGoto (GotoStmt l)]))

kTransform (StmtBlock (BlockStmt (StmtLabel (Label l) : stmts))) stmtk =
  do stmt' <- kTransform (StmtBlock (BlockStmt stmts)) stmtk
     putBlock l (StmtBlock (BlockStmt [stmt']))
     return (StmtBlock (BlockStmt [StmtGoto (GotoStmt l)]))

kTransform (StmtBlock (BlockStmt (stmt : stmts))) stmtk =
  do stmt'  <- kTransform stmt (StmtBlock (BlockStmt []))
     stmts' <- kTransform (StmtBlock (BlockStmt stmts)) stmtk
     return (StmtBlock (BlockStmt [stmt',stmts']))

kTransform stmt stmtk = 
  return (StmtBlock (BlockStmt [stmt, stmtk]))


kTransformMaybe :: Maybe Stmt -> Stmt -> K (Maybe Stmt)
kTransformMaybe (Just stmt) stmtk =
  do stmt' <- kTransform stmt stmtk
     return (Just stmt')

kTransformMaybe Nothing stmtk =
  return Nothing

-----
main = case kTransform spagetti (StmtBlock (BlockStmt [])) of
         K f -> let (stmt, (_,blockmap)) = f (1,[]) in
                do mapM_ prBlock blockmap 
                   putStrLn "main:"
                   putStrLn (show stmt)

prBlock (label, stmt) =
 do putStrLn (label ++ ":")
    putStrLn (show stmt)

-----

helloworld =
  StmtExpr (ExprStmt 
    (ExprMethodCall (MethodCallExpr 
       "TextWindow" "WriteLine" 
          [ExprCond (CondExprLit (Lit "Hello World"))])))

-----

for = 
  StmtFor (ForStmt
    (Var "I") 
    (ExprCond (CondExprLit (Lit "1"))) 
    (ExprCond (CondExprLit (Lit "10")))
    (Just (ExprCond (CondExprLit (Lit "2"))))
    (StmtExpr (ExprStmt 
      (ExprMethodCall (MethodCallExpr 
        "TextWindow" "WriteLine" 
          [ExprVar (Var "I")])))))

-----

while =
  StmtBlock (BlockStmt
  [
    StmtAssign (AssignStmt 
     (ExprVar (Var "I")) (ExprCond (CondExprLit (Lit "1"))))
  , StmtWhile (WhileStmt
     (CondExprComp
       (CompExpr 
         Less_Than (ExprVar (Var "I")) (ExprCond (CondExprLit (Lit "1")))))
     (StmtBlock (BlockStmt
       [
         StmtExpr (ExprStmt 
           (ExprMethodCall (MethodCallExpr 
           "TextWindow" "WriteLine" 
           [ExprCond (CondExprLit (Lit "Hello"))])))

       , StmtAssign 
           (AssignStmt 
             (ExprVar (Var "I")) 
             (ExprArith (ArithExpr 
               Plus
               (ExprVar (Var "I"))
               (ExprCond (CondExprLit (Lit "1"))))))
       ])))
  ])

-----

spagetti = 
  StmtBlock (BlockStmt
  [
     StmtAssign (AssignStmt 
     (ExprVar (Var "I")) (ExprCond (CondExprLit (Lit "1"))))
  ,  StmtLabel (Label "L1")
  ,  StmtAssign (AssignStmt 
     (ExprVar (Var "I")) (ExprCond (CondExprLit (Lit "2"))))
  ,  StmtGoto (GotoStmt "L2")

  ,  StmtLabel (Label "L2")
  ,  StmtAssign (AssignStmt 
     (ExprVar (Var "I")) (ExprCond (CondExprLit (Lit "3"))))
  ,  StmtGoto (GotoStmt "L1")
  ])