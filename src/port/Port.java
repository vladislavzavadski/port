package port;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ship.Ship;
import warehouse.Container;
import warehouse.Warehouse;

public class Port {
	private final static Logger logger = LogManager.getLogger();
	private List<Berth> berthList; // очередь причалов
	private Warehouse portWarehouse; // хранилище порта
	private Map<Ship, Berth> usedBerths; // какой корабль у какого причала стоит

	public Port(int berthSize, int warehouseSize) {
		portWarehouse = new Warehouse(warehouseSize); // создаем пустое
														// хранилище
		berthList = new ArrayList<Berth>(berthSize); // создаем очередь причалов
		for (int i = 0; i < berthSize; i++) { // заполняем очередь причалов
												// непосредственно самими
												// причалами
			berthList.add(new Berth(i, portWarehouse));
		}
		usedBerths = new HashMap<Ship, Berth>(); // создаем объект, который
													// будет
		logger.debug("Порт создан.");
	}


	  public void setContainersToWarehouse(List<Container> containerList){
	  	portWarehouse.addContainer(containerList);
	  }


	public boolean lockBerth(Ship ship) throws InterruptedException {
		boolean result = false;
		Berth berth = null;
		//!!!!!!!!!
		synchronized (berthList) {
			if(berthList.isEmpty()){
				berthList.wait();
			}
			try {
				berth = berthList.remove(0);
			}
			catch (IndexOutOfBoundsException ex){
				return false;
			}
		}
		
		if (berth != null) {
			result = true;
			synchronized (usedBerths) {
				usedBerths.put(ship, berth);
			}
		}
		
		return result;
	}

	public boolean unlockBerth(Ship ship) {
		Berth berth = null;
		synchronized (usedBerths) {
			berth = usedBerths.remove(ship);
		}
		synchronized (berthList) {
			berthList.add(berth);
			berthList.notify();
		}

		return true;
	}

	public Berth getBerth(Ship ship) throws PortException {
		Berth berth;
		synchronized (usedBerths) {
			berth = usedBerths.get(ship);
		}
		if (berth == null) {
			throw new PortException("Try to use Berth without blocking.");
		}
		return berth;
	}
}
