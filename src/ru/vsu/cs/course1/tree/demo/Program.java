package ru.vsu.cs.course1.tree.demo;

import java.awt.*;

import static java.lang.System.in;
import static java.lang.System.out;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.*;

import ru.vsu.cs.course1.tree.bst.SimpleBSTreeMap;
import ru.vsu.cs.util.JTableUtils;


public class Program {


    public static String[][] newReading(String path) throws Exception {

        // Каждая строка массива = строке файла, и содержит информацию о отельном планшете

        File file = new File(path); // Создается File, после по нему создается сканнер
        Scanner scn = new Scanner(file);
        String[][] array = new String[1][1];

        if (file.length() == 0) { // Проверка на пустоту файла
            out.println("File if empty");
            return null;

        } else
        {

            String line = scn.nextLine(); // Сканнер считывает первую строку

            List<String> list = new ArrayList<>(); // Создается список строк(это удобно , поскольку:
            // а) данные нужно занести в массив, длину которого мы не знаем, а список такую проблему не ставит
            // б) получив список с нужными значениями, не будет необходимости создавать новый сканнер и проходиться по файлу вновь(если бы мы в одном прогоне посчитали бы число строк, по нему создали бы массив и еще раз прошлись по файлу)
            list.add(line);  // Строка заносится в список

            while (scn.hasNextLine()) {    // Первый прогон, занесение данных в список
                line = scn.nextLine();
                list.add(line);
            }


            String[][] helpArray = new String[list.size()][list.get(0).split(",").length]; // Создается строковый массив по длине списка

            for (int i = 0; i < list.size(); i++) {   // Из списка в массив передаются данные
                String[] newHelpArray = list.get(i).split(",");
                for (int j=0;j<newHelpArray.length;j++){
                    // out.println(newHelpArray[j]);
                    //  helpArray[i][j]=Double.parseDouble(newHelpArray[j]) ;

                    helpArray[i][j]= newHelpArray[j];
                }
            }
            array=helpArray;
        }
        return array;

    }

    public static Set<String> arrayToSetOfKeys(String [][] list){
        Set<String > set = new HashSet<>();

        for (int i=0;i< list.length;i++){

            for (int j=0;j<list[i].length;j++){
                set.add(list[i][j]);
            }

        }
        return set;
    }


    public static Map<String, Map<String, String>> toMap(String [][] list){

        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

        Set<String > set = arrayToSetOfKeys(list);

        for (String s : set){

            Map<String, String> tempMap = new HashMap<>();

            String name=s;
            String subject=s;
            String mark=s;

            for (int i=0;i< list.length;i++){

                for (int j =0;j<list[0].length;j++){

                        if (list[i][j].equals("offset")||list[i][j].equals("fail"))
                            mark=list[i][j];
                        else   if (list[i][j].split(" ").length>1)
                            name=list[i][j];
                         else subject=list[i][j];

                }

                if (s.equals(name))
                tempMap.put(subject,mark);
            }

            map.put(s,tempMap);

        }

        return map;
    }


    public static List<String> arrayToListOfKeys(String[][] array){

        Set<String > set = new HashSet<>();

        for (int i=0;i< array.length;i++){

            for (int j=0;j<array[i].length;j++){
                set.add(array[i][j]);
            }

        }

        List<String> answer = new ArrayList<>();

        for (String s : set){

          //  SimpleBSTreeMap<String, String> tempMap = new SimpleBSTreeMap<>();

            if (!s.equals("offset")&&!s.equals("fail") && s.split(" ").length>1) answer.add(s);

        }

        return answer;
    }


    public static SimpleBSTreeMap<String , SimpleBSTreeMap<String , String> > toSolomatinMap(String [][] list){

        SimpleBSTreeMap<String, SimpleBSTreeMap<String, String>> map = new SimpleBSTreeMap<String, SimpleBSTreeMap<String, String>>();

        Set<String > set = new HashSet<>();

        for (int i=0;i< list.length;i++){

            for (int j=0;j<list[i].length;j++){
                set.add(list[i][j]);
            }

        }

        for (String s : set){

            SimpleBSTreeMap<String, String> tempMap = new SimpleBSTreeMap<>();

            String name=s;
            String subject=s;
            String mark=s;

            for (int i=0;i< list.length;i++){

                for (int j =0;j<list[0].length;j++){

                    if (list[i][j].equals("offset")||list[i][j].equals("fail"))
                        mark=list[i][j];
                    else   if (list[i][j].split(" ").length>1)
                        name=list[i][j];
                    else subject=list[i][j];

                }

                if (s.equals(name))
                    tempMap.put(subject,mark);
            }

            map.put(s,tempMap);

        }

        return map;

    }


    public static String[][] SolomaKeyToArray(String key,SimpleBSTreeMap<String , SimpleBSTreeMap<String , String> > map){

      //  String[][] array = new String[map.][]
        SimpleBSTreeMap<String , String>  map2 = map.get(key);
        String[][] array = new String[map2.size()][2];

        int i=0;
        for (Map.Entry<String, String> entry : map2.entrySet()){
            array[i][0]=entry.getKey();
            array[i][1]=entry.getValue();
            i++;
        }

        return array;
    }

    public static String[][] BasicKeyToArray(String key,Map<String , Map<String , String> > map){

        Map<String , String>  map2 = map.get(key);
        String[][] array = new String[map2.size()][2];

        int i=0;
        for (Map.Entry<String, String> entry : map2.entrySet()){
            array[i][0]=entry.getKey();
            array[i][1]=entry.getValue();
            i++;
        }

        return array;

    }


    public static void main(String[] args) throws Exception {


        Locale.setDefault(Locale.ROOT);

        String[][] arr = newReading(".\\text.cvs");

        Map<String, Map<String, String>> map = toMap(arr);


        JFrame testFrame = new JFrame();
        JTable table = new JTable();
   //     JTableUtils.writeArrayToJTable(MainTable, finalPCTArray);
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        testFrame.getContentPane().add(table, BorderLayout.CENTER);
        testFrame.setPreferredSize(new Dimension(500,200));


        JMenuBar menuBar = new JMenuBar();
        testFrame.setJMenuBar(menuBar);

        JMenu BarForButtons = new JMenu("Select Student");
        menuBar.add(BarForButtons);

        final boolean[] isBasic = {true};



        for (int i = 0; i< arrayToListOfKeys(arr).size(); i++){

            JMenuItem button = new JMenuItem(""+arrayToListOfKeys(arr).get(i));

            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isBasic[0]) {
                        Map<String, Map<String, String>> map = toMap(arr);
                        String [][] info = BasicKeyToArray(""+arrayToListOfKeys(arr).get(finalI),map);
                        JTableUtils.writeArrayToJTable(table, info);
                    } else {
                        SimpleBSTreeMap<String , SimpleBSTreeMap<String , String> > map = toSolomatinMap(arr);
                        String [][] info = SolomaKeyToArray(""+arrayToListOfKeys(arr).get(finalI),map);
                        JTableUtils.writeArrayToJTable(table, info);
                    }

                    testFrame.setPreferredSize(table.getPreferredSize());
                }
            });
                    BarForButtons.add(button);
        }

        JMenuItem selectBasicMap = new JMenuItem("BasicMap");
        selectBasicMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isBasic[0] =true;
            }
        });

        JMenuItem selectSolomatinMap = new JMenuItem("SolomatinMap");
        selectSolomatinMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                isBasic[0]=false;
            }
        });

        BarForButtons.add(selectBasicMap);
        BarForButtons.add(selectSolomatinMap);


        testFrame.pack();
        testFrame.setVisible(true);

    }
}
