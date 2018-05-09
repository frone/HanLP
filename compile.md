编译说明
----

### clone 源码 下载jar
```
java -cp hanlp.jar com.hankcs.hanlp.model.perceptron.Main -task CWS -train -reference data/test/pku98/199801.txt -model data/test/perceptron/cws.bin
java -cp hanlp.jar com.hankcs.hanlp.model.perceptron.Main -task POS -train -reference data/test/pku98/199801.txt -model data/test/perceptron/pos.bin
java -cp hanlp.jar com.hankcs.hanlp.model.perceptron.Main -task NER -train -reference data/test/pku98/199801.txt -model data/test/perceptron/ner.bin
```