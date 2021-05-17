package com.example.sapimarket.data;

import com.example.sapimarket.ui.home.Model;

import java.util.ArrayList;
import java.util.List;

public class ModelCache {

    private final static String TAG = ModelCache.class.getSimpleName();
    private static ModelCache modelCache;
    private List<Model> modelList;

    private ModelCache() {}

    public synchronized static ModelCache getInstance() {
        if ( modelCache == null) {
            modelCache = new ModelCache();
        }
        return  modelCache;
    }

    public List<Model> getModelList() {
        return modelList;
    }

    public void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }

    public void loadData() {
        modelList = new ArrayList<>();
        Model model = new Model("https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", "Laptop Asus", "Elektronika", "1200", "fdifdihw", "Kis12");
        Model model1 = new Model("https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", "Laptop2", "Elektronika", "1400", "fdifdihw", "Kis12");
        Model model2 = new Model("https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", "Laptop", "Elektronika", "100", "fdifdihw", "Kis12");
        Model model3 = new Model("https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", "Laptop3", "Elektronika", "1000", "fdifdihw", "Kis12");
        Model model4 = new Model("https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", "Laptop1", "Elektronika", "1800", "fdifdihw", "Kis12");
        Model model5 = new Model("https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", "Laptop5", "Elektronika", "2500", "fdifdihw", "Kis12");
        modelList.add(model);
        modelList.add(model1);
        modelList.add(model2);
        modelList.add(model3);
        modelList.add(model4);
        modelList.add(model5);

        setModelList(modelList);
    }

    public Model getModel(String name) {
        for (Model model : modelList) {
            if (model.getName().equals(name)) return model;
        }
        return null;
    }
}
