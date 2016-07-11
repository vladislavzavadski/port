package port;

import warehouse.Container;
import warehouse.Warehouse;

import java.util.List;

public class Berth {

	private int id;
	private Warehouse portWarehouse;

	public Berth(int id, Warehouse warehouse) {
		this.id = id;
		portWarehouse = warehouse;
	}

	public int getId() {
		return id;
	}

	public boolean add(Warehouse shipWarehouse, int numberOfContainers) throws InterruptedException {
		synchronized (portWarehouse) {
			return containerReloading(shipWarehouse, portWarehouse, numberOfContainers);
		}
	}

	public boolean get(Warehouse shipWarehouse, int numberOfContainers) throws InterruptedException {
		synchronized (portWarehouse){
			return containerReloading(portWarehouse, shipWarehouse, numberOfContainers);
		}
	}

	private boolean containerReloading(Warehouse warehouseFrom, Warehouse warehouseTo, int numberOfContainers){
		if(numberOfContainers==0){
			return true;
		}
		List<Container> containers = warehouseFrom.getContainer(numberOfContainers);
		if(containers==null){
			return false;
		}
		return warehouseTo.addContainer(containers);
	}
	
	
}
