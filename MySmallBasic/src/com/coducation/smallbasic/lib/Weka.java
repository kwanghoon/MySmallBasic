package com.coducation.smallbasic.lib;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Weka {
	static int classindex;

	public static void Update(ArrayList<Value> args) {
		// 파일의 전체내용을 읽어서 반환

		if (args.size() == 2) {
			try {
				if(args.get(0) instanceof StrV) {
					if(args.get(1) instanceof ArrayV) {
						DataSource source = new DataSource(args.get(0).toString());
						Instances data = source.getDataSet();

						classindex = data.numAttributes() - 1;
						if (data.classIndex() == -1)
							data.setClassIndex(classindex);

						Instance nInstance = new DenseInstance(data.numAttributes());
						for(int i=0; i<((ArrayV)args.get(1)).size(); i++) { // 입력
							nInstance.setValue(data.attribute(i), ((ArrayV)args.get(1)).get( ((ArrayV)args.get(1)).getKey().get(i+1+"").toString() ).toString()); 
						}
						data.add(nInstance);

						PrintWriter pw = new PrintWriter(new FileOutputStream(args.get(0).toString(), true));
						pw.println(data.lastInstance());
						pw.flush();
						pw.close();

					}
					else throw new InterpretException("Update: Unexpected arg(1)");
				}
				else throw new InterpretException("Update: Unexpected arg(0)");
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		else throw new InterpretException("Update: Unexpected # of args: " + args.size());

	}

	public static Value Classify(ArrayList<Value> args) {
		// 분류, 인자1 : 기반데이터, 인자2 : 테스트데이터(배열)

		String result = "";

		if (args.size() == 2) {
			try {
				if(args.get(0) instanceof StrV) {
					if(args.get(1) instanceof ArrayV) {
						DataSource source = new DataSource(args.get(0).toString());
						Instances data = source.getDataSet();
						
						if(data.numAttributes()-1 == ((ArrayV)args.get(1)).size() ) {
							classindex = data.numAttributes() - 1;
							if (data.classIndex() == -1)
								data.setClassIndex(classindex);

							//Build the classifier
							Classifier model = (Classifier)new RandomForest();   
							model.buildClassifier(data);

							//Create test data
							Instances Idata = new Instances(data, 10); // 지정 instances를 기반으로 한 빈 instances 만들기
							Idata.setClassIndex(classindex);

							Instance testInstance = new DenseInstance(data.numAttributes());

							for(int i=0; i<((ArrayV)args.get(1)).size(); i++) { // 
								testInstance.setValue(data.attribute(i), ((ArrayV)args.get(1)).get( ((ArrayV)args.get(1)).getKey().get(i+1+"").toString() ).toString()); 
							}

							Idata.add(testInstance);

							//Classify new Instance
							double ClassLabel = -1;

							ClassLabel = model.classifyInstance(Idata.instance(0));

							// 리턴값(숫자랑 문자일 때 다름)
							if(Idata.attribute(classindex).isNominal())
								result = Idata.attribute(classindex).value((int) ClassLabel);
							else result = ClassLabel+"";
						}
						else throw new InterpretException("Classify: Unequal data format"); // 데이터형식이 같지않음
					}
					else throw new InterpretException("Classify: Unexpected arg(1)");
				}
				else throw new InterpretException("Classify: Unexpected arg(0)");
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		else throw new InterpretException("Classify: Unexpected # of args: " + args.size());

		return new StrV(result);
	}
}
