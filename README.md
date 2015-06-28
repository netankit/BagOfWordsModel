# BagOfWordsModel
The bag-of-words model is a simplifying representation used in natural language processing and information retrieval (IR).

In this model, a text (such as a sentence or a document) is represented as the bag 
(multiset) of its words, disregarding grammar and even word order but keeping multiplicity. 
Recently, the bag-of-words model has also been used for computer vision.[1] The bag-of-words model 
is commonly used in methods of document classification, where the (frequency of) occurrence of each 
word is used as a feature for training a classifier.

NOTE: Scikit learn provides a much poptimized version for this task. Please refer here: 
CountVectorizer : http://scikit-learn.org/stable/modules/generated/sklearn.feature_extraction.text.CountVectorizer.html
and
TF-IDF Vectorizer: http://scikit-learn.org/stable/modules/generated/sklearn.feature_extraction.text.TfidfVectorizer.html
