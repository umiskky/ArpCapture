package com.umiskky.viewmodel;

import com.umiskky.model.ModelFactory;
import com.umiskky.viewmodel.main.MainViewModel;

/**
 * @author UmiSkky
 */
public class ViewModelFactory {
    private MainViewModel mainViewModel;

    public ViewModelFactory(ModelFactory modelFactory){
        mainViewModel = new MainViewModel(modelFactory.getDateModel());
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }
}
