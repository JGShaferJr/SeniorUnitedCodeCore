package de.fu_berlin.inf.dpp.ui.widgets.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FacsvatarNProxyLauncher implements Runnable {

    String ip_address;
    public Process process;
    String pid;

    public FacsvatarNProxyLauncher(String ip_address) {
        super();
        this.ip_address = ip_address;
    }

    public void KillSubProcesses() {
        String cmd = "taskkill /F /PID " + pid;
        System.out.println("NProxy Kill cmd: \"" + cmd + "\"");
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {

        }
    }

    @Override
    public void run() {

        // TODO Auto-generated method stub
        // String[] arguments = { "python",
        // "FACsvatar_client_interface\\modules\\n_proxy_m_bus.py", "--sub_ip",
        // ip_address };
        String[] arguments = { AudioProducer.getPythonFile("run_n_proxy.bat"),
            AudioProducer.getPythonFile("."), ip_address };
        try {
            ProcessBuilder procBuild = new ProcessBuilder(arguments);
            procBuild.redirectErrorStream(true);
            process = procBuild.start();

            String line;

            BufferedReader input = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (line.startsWith("PID: ")) {
                    pid = line.replace("PID: ", "");
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}