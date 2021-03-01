package com.api.sensors;

import com.Telemetry;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import util.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.List;

class SystemInfoImpl implements SystemInfo {
    private Components components;
    List<Temperature> temps;
    List<Cpu> cpus;


    @Override
    public void loadSensors() {
        Logger.Log_ln("Call to loadSensors, in progress", Logger.Level.DEBUG, Logger.Type.SYSTEM);
        components = JSensors.get.components();
        cpus = components.cpus;
        Logger.Log_ln("Done to loadSensors, in progress", Logger.Level.DEBUG, Logger.Type.SYSTEM);
    }

    @Override
    public double getInternetPing() {
        long timeToRespond = 0x0;
        try {
            String hostAddress = "1.1.1.1";
            int port = 80;

            InetAddress inetAddress = InetAddress.getByName(hostAddress);
            InetSocketAddress socketAddress = new InetSocketAddress(inetAddress, port);

            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(true);

            Date start = new Date();
            if (sc.connect(socketAddress)) {
                Date stop = new Date();
                timeToRespond = (stop.getTime() - start.getTime());
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return (double) timeToRespond;
    }

    @Override
    public synchronized int getCPUTemp() {
        loadSensors();
        int allNumbers;
        if(components != null && cpus != null){
        int size = 0;
        double[] temparray;
            for (final Cpu cpu : cpus) {
                if (cpu.sensors != null) {
                    temps = cpu.sensors.temperatures;
                    for (final Temperature ignored : temps) {
                        size++;
                    }
                    if (size != 0) {
                        allNumbers = 0;
                        temparray = new double[size];
                        for (int i = 1; i < temps.size(); i++) {
                            temparray[i] = temps.get(i).value;
                        }
                        if (temparray.length != 1) {
                            int i;
                            for (i = 0; i < temparray.length; i++) {
                                allNumbers = allNumbers + (int) temparray[i];
                            }

                            Logger.Log_ln("Call to SystemInfoImpl, returned value: " + allNumbers / i + " bufured numbers: " + allNumbers, Logger.Level.DEBUG, Logger.Type.SYSTEM);
                            return allNumbers / i;
                        }
                    }

                }
            }
        }
        return 0;
    }

    @Override
    public double[] getDailyTemp() {
        return Telemetry.getInstance().getDayTemp();
    }

    @Override
    public byte getDiskTemp() {
        return 0;
    }

    @Override
    public long getUsedMemory() {
        return (getTotalMemory() - getFreeMemory());
    }

    @Override
    public long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    @Override
    public long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    @Override
    public String getCPUName() {
        for(final Cpu cpu : cpus){
            return cpu.name;
        }

        return null;
    }
}
