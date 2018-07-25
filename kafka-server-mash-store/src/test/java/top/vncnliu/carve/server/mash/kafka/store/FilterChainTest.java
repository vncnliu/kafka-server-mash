/*
package top.vncnliu.carve.server.mash.kafka.store;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * User: liuyq
 * Date: 2018/7/24
 * Description:
 *//*

public class FilterChainTest {

    @Test
    public void main(){
        ChainT chainT = new ChainT();
        chainT.add(new FilterT("1"));
        chainT.add(new FilterT("2"));
        chainT.add(new FilterT("3"));
        chainT.doF();
    }

    class ChainT {
        List<FilterT> list=new ArrayList<>();

        public void add(FilterT filterT){
            list.add(filterT);
        }
        int i=0;

        public void doF(){

            i++;
            if(i==list.size()+1){
                System.out.println("end");
            }else {
                list.get(i-1).exe(this);
            }

        }
    }

    class FilterT {
        public String id;

        public FilterT(String id) {
            this.id = id;
        }

        public void exe(ChainT chainT){
            System.out.println("befor"+id);
            if("2".equals(id)){
                throw  new IllegalArgumentException();
            }
            chainT.doF();
            System.out.println("after"+id);
        }
    }
}

*/
