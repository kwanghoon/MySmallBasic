package com.coducation.smallbasic.lib;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class File {

	public static Value ReadContents(ArrayList<Value> args) {
		// 파일의 전체내용을 읽어서 반환

		StringBuilder s = new StringBuilder("");

		if (args.size() == 1) {
			try {
				if(args.get(0) instanceof StrV) {
					InputStreamReader isr = new InputStreamReader(new FileInputStream(args.get(0).toString()), "UTF-8");
					int b;
					while((b=isr.read())!=-1)
						s.append((char)b);
					isr.close();
				}
				else throw new InterpretException("ReadContents: Unexpected arg");
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else throw new InterpretException("ReadContents: Unexpected # of args: " + args.size());

		return new StrV(s.toString());
	}

	public static Value WriteContents(ArrayList<Value> args) {
		// 파일에 내용을 쓰고 성공하면 "SUCCESS", 실패하면 "FAILED" 반환

		if (args.size() == 2) {
			try {
				if(args.get(0) instanceof StrV) {
					if(args.get(1) instanceof StrV || args.get(1) instanceof DoubleV) {
						OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(args.get(0).toString()), "UTF-8");
						osr.write(args.get(1).toString());
						osr.flush();
						osr.close();
						return new StrV("SUCCESS");
					}
					else { /*
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(args.get(0).toString()));
				for(int i=0;i<((ArrayV)args.get(1)).)
				dos.writeBytes(((ArrayV)args.get(1)).get(0).toString());
				dos.flush();
				dos.close();
				return new StrV("SUCCESS");*/
					}
				}
				else throw new InterpretException("WriteContents: Unexpected arg(0)");
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else throw new InterpretException("WriteContents: Unexpected # of args: " + args.size());

		return new StrV("FAILED");
	}

	public static Value ReadLine(ArrayList<Value> args) {
		// 파일의 특정라인을 읽어서 반환
		StringBuilder s = new StringBuilder("");

		if (args.size() == 2) {
			try {
				if(args.get(0) instanceof StrV) {
					String arg1;
					if(args.get(1) instanceof StrV || args.get(1) instanceof DoubleV)
						arg1 = args.get(1).toString();
					else arg1="1";
					if(arg1.equals("0")) arg1="1"; // 라인번호 0과 1은 처음을 의미

					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args.get(0).toString()), "UTF-8"));
					String b;
					int n = 0; // 라인수를 의미
					while((b=br.readLine())!=null) {
						n++;
						if((n+"").equals(arg1))
							s.append(b+"\r\n");
					}
					br.close();
				}
				else throw new InterpretException("ReadLine: Unexpected arg(0)");
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else throw new InterpretException("ReadLine: Unexpected # of args: " + args.size());

		return new StrV(s.toString());
	}

	public static Value WriteLine(ArrayList<Value> args) {
		// 지정 파일을 읽고 지정된 줄 번호에 내용을 덮어씀
		StringBuilder s = new StringBuilder("");

		if (args.size() == 3) {
			try {
				if(args.get(0) instanceof StrV) {
					if(args.get(1) instanceof StrV || args.get(1) instanceof DoubleV) {
						if(args.get(2) instanceof StrV || args.get(2) instanceof DoubleV) {
							java.io.File f = new java.io.File(args.get(0).toString());
							if(!f.exists()) {
								OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(args.get(0).toString()), "UTF-8");
								osr.write(args.get(2).toString()+"\r\n");
								osr.flush();
								osr.close();
							}
							else {
								BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args.get(0).toString()), "UTF-8"));
								String b;
								int n = 0; // 라인수를 의미
								while((b=br.readLine())!=null) {
									n++;
									if((n+"").equals(args.get(1).toString()))
										s.append(args.get(2).toString()+"\r\n");
									else s.append(b+"\r\n");
								}
								if(Integer.parseInt(args.get(1).toString())>=n+1)
									s.append(args.get(2).toString()+"\r\n");
								br.close();

								OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(args.get(0).toString()), "UTF-8");
								osr.write(s.toString());
								osr.flush();
								osr.close();
							}
							return new StrV("SUCCESS");
						}
						else System.out.print("args(2) : 배열 인자입니다."); // 가능
					}
					else throw new InterpretException("WriteLine: Unexpected arg(1)");
				}
				else throw new InterpretException("WriteLine: Unexpected arg(0)");
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else throw new InterpretException("WriteLine: Unexpected # of args: " + args.size());

		return new StrV("FAILED");
	}

	public static Value InsertLine(ArrayList<Value> args) {
		// 지정 파일을 열고 지정된 줄 번호에 내용을 삽입
		StringBuilder s = new StringBuilder("");

		if (args.size() == 3) {
			try {
				if(args.get(0) instanceof StrV) {
					if(args.get(1) instanceof StrV || args.get(1) instanceof DoubleV) {
						if(args.get(2) instanceof StrV || args.get(2) instanceof DoubleV) {
							java.io.File f = new java.io.File(args.get(0).toString());
							if(!f.exists()) {
								OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(args.get(0).toString()), "UTF-8");
								osr.write(args.get(2).toString()+"\r\n");
								osr.flush();
								osr.close();
							}
							else {
								BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(args.get(0).toString()), "UTF-8"));
								String b;
								int n = 0; // 라인수를 의미
								while((b=br.readLine())!=null) {
									n++;
									if((n+"").equals(args.get(1).toString()))
										s.append(args.get(2).toString()+"\r\n");
									s.append(b+"\r\n");
								}
								if(Integer.parseInt(args.get(1).toString())>=n+1)
									s.append(args.get(2).toString()+"\r\n");
								br.close();

								OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(args.get(0).toString()), "UTF-8");
								osr.write(s.toString());
								osr.flush();
								osr.close();
							}
							return new StrV("SUCCESS");
						}
						else System.out.print("args(2) : 배열 인자입니다."); // 가능
					}
					else throw new InterpretException("InsertLine: Unexpected arg(1)");
				}
				else throw new InterpretException("InsertLine: Unexpected arg(0)");
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else throw new InterpretException("InsertLine: Unexpected # of args: " + args.size());

		return new StrV("FAILED");
	}

	public static Value AppendContents(ArrayList<Value> args) {
		// 지정 파일을 열고 파일의 끝에 내용을 추가
		StringBuilder s = new StringBuilder("");

		if (args.size() == 2) {
			try {
				if(args.get(0) instanceof StrV) {
					if(args.get(1) instanceof StrV || args.get(1) instanceof DoubleV) {
				
							OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(args.get(0).toString(), true), "UTF-8");
							osr.write(args.get(1).toString()+"\r\n");
							osr.flush();
							osr.close();
						
						return new StrV("SUCCESS");
					}
					else System.out.print("배열 인자입니다.");
				}
				else throw new InterpretException("AppendContents: Unexpected arg(0)");
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else throw new InterpretException("AppendContents: Unexpected # of args: " + args.size());

		return new StrV("FAILED");
	}

	public static Value CopyFile(ArrayList<Value> args) {
		// 지정 소스파일을 대상 파일 경로에 복사
		if (args.size() == 2) {
			if(args.get(0) instanceof StrV) {
				if(args.get(1) instanceof StrV) {
					java.io.File f = new java.io.File(args.get(0).toString());
					java.io.File cf = new java.io.File(args.get(1).toString());

					String s = args.get(1).toString().substring(0,args.get(1).toString().lastIndexOf("\\")); // 디렉토리경로
					java.io.File directory = new java.io.File(s);
					if(!directory.exists()) directory.mkdirs(); 

					InputStream inStream = null;
					OutputStream outStream = null;

					try{
						inStream = new FileInputStream(f); //원본파일
						outStream = new FileOutputStream(cf); //이동시킬 위치

						byte[] buffer = new byte[1024];
						int length;

						while ((length = inStream.read(buffer)) > 0){
							outStream.write(buffer, 0, length);
						}
						inStream.close();
						outStream.close();
						return new StrV("SUCCESS");
					}catch(IOException e){
						e.printStackTrace();
					}
				}
				else throw new InterpretException("CopyFile: Unexpected arg(1)");
			}
			else throw new InterpretException("CopyFile: Unexpected arg(0)");
		}
		else throw new InterpretException("CopyFile: Unexpected # of args: " + args.size());

		return new StrV("FAILED");

	}

	public static Value DeleteFile(ArrayList<Value> args) {
		// 지정한 파일을 제거
		if (args.size() == 1) {
			if(args.get(0) instanceof StrV) {
				java.io.File f = new java.io.File(args.get(0).toString());
				if(f.delete()) return new StrV("SUCCESS");
				else return new StrV("FAILED");
			}
			else throw new InterpretException("DeleteFile: Unexpected arg(0)");
		}
		else throw new InterpretException("DeleteFile: Unexpected # of args: " + args.size());
	}

	public static Value CreateDirectory(ArrayList<Value> args) {
		// 지정한 디렉토리를 생성
		if (args.size() == 1) {
			if(args.get(0) instanceof StrV) {
				java.io.File directory = new java.io.File(args.get(0).toString());
				if(directory.mkdirs()) return new StrV("SUCCESS");
				else return new StrV("FAILED");
			}
			else throw new InterpretException("CreateDirectory: Unexpected arg(0)");
		}
		else throw new InterpretException("CreateDirectory: Unexpected # of args: " + args.size());
	}

	public static Value DeleteDirectory(ArrayList<Value> args) {
		// 지정한 디렉토리를 삭제
		if (args.size() == 1) {
			if(args.get(0) instanceof StrV) {
				java.io.File directory = new java.io.File(args.get(0).toString());
				if(directory.delete()) return new StrV("SUCCESS");
				else return new StrV("FAILED");
			}
			else throw new InterpretException("DeleteDirectory: Unexpected arg(0)");
		}
		else throw new InterpretException("DeleteDirectory: Unexpected # of args: " + args.size());
	}

	public static Value GetFiles(ArrayList<Value> args) {
		// 지정한 디렉토리 경로안의 모든 파일들의 경로을 가져옴 성공하면 파일들이 배열로서 반환
		try {
			if (args.size() == 1) {
				if(args.get(0) instanceof StrV) {
					java.io.File directory = new java.io.File(args.get(0).toString());
					java.io.File[] files = directory.listFiles();
					ArrayV v = new ArrayV();
					for(int i=0;i<files.length;i++) {
						if(files[i].isFile())
							v.put(i+"",new StrV(files[i].getCanonicalPath()));
					}
					return v;
				}
				else throw new InterpretException("GetFiles: Unexpected arg(0)");
			}
			else throw new InterpretException("GetFiles: Unexpected # of args: " + args.size());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return new StrV("FAILED");
	}

	public static Value GetDirectories(ArrayList<Value> args) {
		// 지정한 디렉토리 경로 안에 있는 모든 디렉토리들의 경로를 가져옴 성공하면 디렉토리경로들이 배열로서 반환
		try {
			if (args.size() == 1) {
				if(args.get(0) instanceof StrV) {
					java.io.File directory = new java.io.File(args.get(0).toString());
					java.io.File[] files = directory.listFiles();
					ArrayV v = new ArrayV();
					for(int i=0;i<files.length;i++) {
						if(files[i].isDirectory())
							v.put(i+"",new StrV(files[i].getCanonicalPath()));
					}
					return v;
				}
				else throw new InterpretException("GetDirectories: Unexpected arg(0)");
			}
			else throw new InterpretException("GetDirectories: Unexpected # of args: " + args.size());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return new StrV("FAILED");
	}

	public static Value GetTemporaryFilePath() {
		// 새로운 임시 파일을 임시 디렉터리에 생성하고 전체 파일 경로값을 반환
		java.io.File tmp;
		String path = null;
		try {
			tmp = java.io.File.createTempFile("tmp", null);
			path = tmp.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new StrV(path);
	}

	public static Value GetSettingsFilePath() {
		// 이 프로그램에 대한 설정 파일의 전체 경로값을 가져옵니다.


		return new StrV("SUCCESS");
	}

}
