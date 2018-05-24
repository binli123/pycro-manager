/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.org.micromanager.plugins.magellan.autofocus;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.types.UInt8;

/**
 *
 * @author Henry
 */
public class SingleShotAutofocus {

    private static SingleShotAutofocus singleton_;

    public SingleShotAutofocus() {
        singleton_ = this;
    }

    public double predictDefocus(short[] image) {

        return 0.0;
    }

    public static SingleShotAutofocus getInstance() {
        return singleton_;
    }

    public void loadModel(File f) {
        //either load model or throw exception



//            Graph g = new Graph();
//            final String value = "Hello from " + TensorFlow.version();
//
//            // Construct the computation graph with a single operation, a constant
//            // named "MyConst" with a value "value".
//            Tensor t = Tensor.create(value.getBytes("UTF-8"));
//            // The Java API doesn't yet include convenience functions for adding operations.
//            g.opBuilder("Const", "MyConst").setAttr("dtype", t.dataType()).setAttr("value", t).build();
//
//            // Execute the "MyConst" operation in a Session.
//            Session s = new Session(g);
//            Tensor output = s.runner().fetch("MyConst").run().get(0);
//            System.out.println(new String(output.bytesValue(), "UTF-8"));

      

        //store preferred model path so it can be reloaded on startup
    }

    public String getModelName() {
        return "TODO";
    }

    public static void main(String[] args) {

        String modelDir = "";
        String imageFile = "";

        //read graph def generated by python API
        byte[] graphDef = readAllBytesOrExit(Paths.get(modelDir, "C:/Users/Henry/Documents/GitHub/Leukosight/analysis/autofocus/exported_model/saved_model.pb"));
        Graph g = new Graph();
        g.importGraphDef(graphDef);
        Session s = new Session(g);
          
        short[] data = new short[1024];
        
        Tensor input = Tensor.create(data);
        // Generally, there may be multiple output tensors, all of them must be closed to prevent resource leaks.
        Tensor<Float> predictedDefocusTensor = s.runner().feed("input", input).fetch("output").run().get(0).expect(Float.class);

        double predictedDefocus = predictedDefocusTensor.doubleValue();
        

        //TODO: close all resources
    }


    private static byte[] readAllBytesOrExit(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("Failed to read [" + path + "]: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

}
