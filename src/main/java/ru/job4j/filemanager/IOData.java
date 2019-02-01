package ru.job4j.filemanager;

import java.io.*;

public class IOData {

    private final OutputStream out;
    private final InputStream in;
    public final static String MENU_MARK = "MENU---/";
    public final static String STRING_MARK = "STRING---/";
    public final static String OBJECT_MARK = "OBJECT---/";
    public final static String FILE_MARK = "FILE---/";
    public final static String REQ_FILE_MARK = "REQ_FILE---/";


    public IOData(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void sendString(String str) {
        this.sendStr(STRING_MARK);
        this.sendStr(str);
    }

    private void sendStr(String str) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            bw.write(str + System.lineSeparator());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMenu(String str) {
        this.sendStr(MENU_MARK);
        this.sendStr(str);
    }

    public void sendInt(int value) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            bw.write(String.valueOf(value));
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void sendObject(T obj) {
        this.sendStr(OBJECT_MARK);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(File file, String fileName) {
        this.sendStr(FILE_MARK + fileName);
        try (FileReader rf = new FileReader(file)) {
            BufferedOutputStream bos = new BufferedOutputStream(out);
            int c = 0;
            while (c != -1) {
                c = rf.read();
                bos.write(c);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString() {
        return this.getStr();
    }

    public void sendRequest(String fileName) {
        this.sendStr(REQ_FILE_MARK + fileName);
    }

    public String getMenu() {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String str = br.readLine();
            while (!str.isEmpty()) {
                result.append(str);
                result.append(System.lineSeparator());
                str = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private String getStr() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ("" + System.lineSeparator());
    }

    public int getInt() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            return Integer.valueOf(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void getFile(File curDir, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(new File(curDir + File.separator + fileName))) {
            BufferedInputStream bis = new BufferedInputStream(in);
            int c;
            while ((c = (byte) bis.read()) != -1) {
                fos.write(c);
            }
            fos.write(-1);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject() {
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
