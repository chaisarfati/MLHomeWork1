package HomeWork1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class MainHW1 {

	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;

		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}

		return inputReader;
	}

	/**
	 * Sets the class index as the last attribute.
	 * @param fileName
	 * @return Instances data
	 * @throws IOException
	 */
	public static Instances loadData(String fileName) throws IOException{
		BufferedReader datafile = readDataFile(fileName);

		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		return data;
	}


	public static void main(String[] args) throws Exception {

		double trainError;
		double testError;
		//Instances trainingData;
		//Instances testingData;

			//load data
			Instances trainingData = loadData
					("/Users/ArielleRebibo/Documents/workspace" +
					"/HomeWork1/out/production/HomeWork1/HomeWork1" +
							"/wind_training" +
							".txt");
			Instances testingData = loadData
					("/Users/ArielleRebibo/Documents/workspace" +
							"/HomeWork1/out/production/HomeWork1/HomeWork1/wind_testing.txt");

//			trainingData = loadData("wind_training.txt");
//			testingData = loadData("wind_testing.txt");

			//find best alpha and build classifier with all attributes
			LinearRegression classifierTraining = new LinearRegression(trainingData);
			LinearRegression classifierTesting = new LinearRegression(testingData);

		classifierTesting.m_coefficients = new double[testingData.numAttributes()];
		classifierTraining.m_coefficients = new double[trainingData.numAttributes()];

		System.out.println("The chosen alpha is: " + classifierTesting.m_alpha);
		System.out.println("Training Error with all features is: " +
				classifierTraining.calculateMSE(trainingData));
		System.out.println("Testing Error with all features is: " +
				classifierTesting.calculateMSE(testingData));

			//build classifiers with all 3 attributes combinations

			double sum = 0;
			double tempError;
			double minError = 0;
			// initialized to 0 arbitrarily, the value will be changed in the first iteration of the k loop
			int[] threeAttributes = new int[3];
			Remove remove = new Remove();
			//String[] options = new String[4];
			//options [0] = "-V";
		int[] rangeList = new int[3];
		remove.setInvertSelection(true);

			for (int i = 1; i < 12; i++) {
				//options[1] = Integer.toString(i);
				rangeList[0] = i;
				for (int j = i + 1; j < 13; j++) {
					//String sj = Integer.toString(j);
					//options[2] = sj;
					rangeList[1] = j;
					for (int k = j + 1; k < 14; k++) {
						//String sk = Integer.toString(k);
						//options[3] = sk;
						rangeList[2] = k;
						//remove.setOptions(options);
						remove.setInputFormat(trainingData);
						remove.setInputFormat(testingData);
						//classifierTraining.buildClassifier(Filter.useFilter
						//(trainingData, remove));
						//classifierTesting.buildClassifier(Filter.useFilter
						//(testingData,
						//	remove));

						remove.setAttributeIndicesArray(rangeList);

						testError = classifierTesting.calculateMSE(testingData);
						trainError = classifierTraining.calculateMSE
								(trainingData);


						if (k == 2) minError = trainError;
						if (trainError < minError)  {
							minError = trainError;
							threeAttributes[0] = i;
							threeAttributes[1] = j;
							threeAttributes[2] = k;
						}
						if (k == 2) minError = testError;
						if (testError < minError)  {
							minError = testError;
							threeAttributes[0] = i;
							threeAttributes[1] = j;
							threeAttributes[2] = k;
						}
					}
				}
			}

			 trainError = minError;
			 testError = minError;
	//	System.out.println("The chosen alpha is: " + classifierTesting.m_alpha);
	}

	}




