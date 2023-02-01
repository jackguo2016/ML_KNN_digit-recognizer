# ML_KNN_digit-recognizer


Programming Task: For each dataset, you must create a K-NN classi_er that uses the training data to build a classi_er, and evaluate and report on the classi_er performance.


There is the changes of error rate under different values of k.

Here the K value is from 1 to 50, and the y-axis represents the error rate on the x-axis.

 
（Now using the Minkowski distance test method）

 
 （The euclidean metric method is now used）


 
（The Manhattan Distance method is now used）

Here is some scatter plots show a big picture of the data:
    

(End of pima-indians-diabetes-database graph)




digit-recognizer dataset:
Here is Error rate of number 8 with different k values:  


I randomly divided the whole database into 20% test set and 80% sample set, The error rate of the learning results at this ratio is much lower than the 50% 50% error rate.



(15 points) Algorithm Description: K-NN is a very clear algorithm, so here describe any data pre-
processing, feature scaling, distance metrics, or otherwise that you did.

data preprocessing:
For the data of these two datasets, I created a unique class in the program called data class. Then put these data into the data class for preprocessing according to the different characteristics of each row of data.

feature scaling:
In the data set about diabetes, there is a feature value of insulin, and his value will vary greatly. So in order to narrow the effect of this value on the overall prediction results, I limited this value to be between zero and 1. In this way, its influence on other eigenvalues can be reduced and the overall accuracy can be improved.

distance metrics:
In k-means or kNN, we often use Euclidean distance to calculate the distance between nearest neighbors, and sometimes Manhattan distance. In the diabetes dataset I used all three distance test methods to better improve the accuracy. Then in the handwritten font recognition dataset, I chose to use the Manhattan distance to calculate the distance based on the accuracy of the diabetes dataset.



Pima Dataset：

This is happen when k = 2 and k = 3.
 
Accuracy is 72% for k = 2.

digit-recognizer dataset:
 
complete confusion matrix:
 
single digit 8 to measure accuracy and number varies as a function of K:  
Accuracy around 80%


For the run-time of your algorithm since the loop in the knn function, So the run-time of it will be O(N).
![image](https://user-images.githubusercontent.com/54688551/216176036-41cb2145-4b18-4741-95a7-97674c0eccd8.png)
