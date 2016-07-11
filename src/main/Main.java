package main;

import java.util.ArrayList;
import java.util.List;

import port.Port;
import ship.Ship;
import warehouse.Container;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		int warehousePortSize = 15;
		List<Container> containerList = new ArrayList<Container>(warehousePortSize);
		for (int i=0; i<warehousePortSize; i++){
			containerList.add(new Container(i));
		}

		Port port = new Port(2, 900);
		port.setContainersToWarehouse(containerList);
		containerList = new ArrayList<Container>(warehousePortSize);
		for (int i=0; i<warehousePortSize; i++){
			containerList.add(new Container(i+30));
		}
		Ship ship1 = new Ship("Ship1", port, 90);
		ship1.setContainersToWarehouse(containerList);
		
		containerList = new ArrayList<Container>(warehousePortSize);
		for (int i=0; i<warehousePortSize; i++){
			containerList.add(new Container(i+60));
		}
		Ship ship2 = new Ship("Ship2", port, 90);
		ship2.setContainersToWarehouse(containerList);
		
		containerList = new ArrayList<Container>(warehousePortSize);
		for (int i=0; i<warehousePortSize; i++){
			containerList.add(new Container(i+90));
		}
		Ship ship3 = new Ship("Ship3", port, 90);
		ship3.setContainersToWarehouse(containerList);
		containerList = new ArrayList<Container>(warehousePortSize);
		for (int i=0; i<warehousePortSize; i++){
			containerList.add(new Container(i+120));
		}
		Ship ship4 = new Ship("Ship4", port, 90);
		ship3.setContainersToWarehouse(containerList);

		containerList = new ArrayList<Container>(warehousePortSize);
		for (int i=0; i<warehousePortSize; i++){
			containerList.add(new Container(i+150));
		}
		Ship ship5 = new Ship("Ship5", port, 90);
		ship3.setContainersToWarehouse(containerList);

		new Thread(ship1).start();		
		new Thread(ship2).start();
		new Thread(ship3).start();
		new Thread(ship4).start();
		new Thread(ship5).start();

		Thread.sleep(20000);
		
		ship1.stopThread();
		ship2.stopThread();
		ship3.stopThread();
		ship4.stopThread();
		ship5.stopThread();

	}

}
