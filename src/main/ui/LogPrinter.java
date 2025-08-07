package ui;

import model.Event;
import model.EventLog;

public class LogPrinter {
	public void printLog(EventLog el) {
		for (Event next : el) {
            System.out.println(next);
        }
	}
}
