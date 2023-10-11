package Observer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MeteoApp {
//    public static void main(String[] args) {
//        MeteoStation station = new MeteoStation();
//
//        station.addObserver(new ConsoleObserver());
//        station.addObserver(new FileObserver());
//        station.setMeasurements(20, 760);
//        station.setMeasurements(-5, 745);
//
//    }
}

interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
interface Observer {
    void handleEvent(int temp, int presser);
}

class MeteoStation implements Observable {

    int temperature;
    int pressure;

    List<Observer> observers = new ArrayList<>();

    public void setMeasurements(int t, int p) {
        this.temperature = t;
        this.pressure = p;
        notifyObservers();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.handleEvent(temperature, pressure);
        }
    }
}

class ConsoleObserver implements Observer {

    @Override
    public void handleEvent(int temp, int pressure) {
        System.out.println("Weather's changed. Temperature = " + temp + " Pressure = " + pressure);
    }
}

class FileObserver implements Observer {

    @Override
    public void handleEvent(int temp, int pressure) {
        File f;
        try {
         f = File.createTempFile("TempPressure", "_txt");
            PrintWriter pw = new PrintWriter(f);
            pw.print("Weather's changed. Temperature = " + temp + " Pressure = " + pressure);
            pw.println();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
