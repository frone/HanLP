package com.hankcs.hospital_handle;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        String text = "很好";
        System.out.println(text.length());

        List list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");

        List list2 = new ArrayList();
        list2.add("3333");
        list2.add("4444");
        list2.add("5555");

        //并集
        //list1.addAll(list2);
        //交集
        System.out.println(intersect(list1,list2));
        System.out.println(list1.size());
        System.out.println(list2.size());
//        System.out.println(list1.retainAll(list2));
//        System.out.println(list1.size());
        //差集
        //list1.removeAll(list2);
        //无重复并集
//        list2.removeAll(list1);
//        list1.addAll(list2);

        Iterator<String> it = list1.iterator();
        while (it.hasNext()) System.out.println(it.next());

        Integer a = 1;
        Integer b= 2;
        System.out.println(a.compareTo(b));

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("c", 3);
        map.put("a", 1);
        map.put("b", 2);
        map.put("d", 4);

        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        //然后通过比较器来实现排序
        list.sort(new Comparator<Map.Entry<String, Integer>>() {//降序序排序
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        System.out.println(list.toString());

        for(Map.Entry<String,Integer> mapping:list.subList(0,1)){
            System.out.println(mapping.getKey()+":"+mapping.getValue());
        }
    }

    public static int intersect(List ls, List ls2) {
        List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
        Collections.copy(list, ls);
        list.retainAll(ls2);
        return list.size();
    }
}
