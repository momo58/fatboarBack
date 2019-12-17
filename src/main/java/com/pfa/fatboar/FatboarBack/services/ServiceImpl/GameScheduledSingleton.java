package com.pfa.fatboar.FatboarBack.services.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class GameScheduledSingleton {
	
	    public List<ScheduledFuture<?>> tasks;

	    public GameScheduledSingleton() {
	        this.tasks = new ArrayList<>();
	    }

}
