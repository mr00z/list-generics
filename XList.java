package ex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class XList<T> extends ArrayList<T>{
	
	public XList(Collection<T> c){
		this.addAll(c);
	}
	
	public XList(T...ts ){
		this.addAll(Arrays.asList(ts));
	}

	public static <T> XList<T> of(T... t) {
		XList<T> list = new XList<T>(t);
		return list;
	}
	
	public static <T> XList<T> of(Collection<T> c) {
		XList<T> list = new XList<T>(c);
		return list;
	}

	public static XList<String> charsOf(String string) {
		XList<String> list= new XList<String>(string.split(""));
		return list;
	}

	public static XList<String> tokensOf(String string) {
		XList<String> list = new XList<String>(string.split("\\s"));
		return list;
	}

	public static XList<String> tokensOf(String string, String string2) {
		XList<String> list = new XList<String>(string.split("["+string2+"]"));
		return list;
	}

	public XList<T> union(Collection<T> l) {
		XList<T> list = new XList<T>();
		list.addAll(this);
		list.addAll(l);
		return list;
	}
	
	public XList<T> union(T[] l) {
		XList<T> list = new XList<T>();
		list.addAll(this);
		list.addAll(Arrays.asList(l));
		return list;
	}

	public XList<T> diff(Collection<T> c) {
		XList<T> list = new XList<T>();
		List<T> col = new ArrayList<T>(c);
		for (int i=0; i<this.size(); i++){
			if (!col.contains(this.get(i))) list.add(this.get(i));
		}
		return list;
	}

	public XList<T> unique() {
		XList<T> list = new XList<T>();
		for (int i=0; i<this.size(); i++){
			if (!list.contains(this.get(i))) list.add(this.get(i));
		}
		return list;
	}

	public XList<String> collect(Function<T, String> f) {
		XList<String> list = new XList<>();
		for (int i=0; i<this.size(); i++){
			list.add( f.apply( (T) this.get(i)));
		}
		return list;
	}

	public XList<Collection<T>> combine() {
	    XList<Collection<T>> resultLists = new XList<Collection<T>>();
	    if (this.size() == 0) {
	        resultLists.add(new XList<T>());
	        return resultLists;
	    } else {
	        Collection<T> firstList =   (Collection<T>) this.get(0);
	        this.remove(0);
	        XList<Collection<T>> remainingLists = this.combine();
	        for (T condition : firstList) {
	            for (Collection<T> remainingList : remainingLists) {
	                XList<T> resultList = new XList<T>();
	                resultList.add(condition);
	                resultList.addAll(remainingList);
	                resultLists.add(resultList);
	            }
	        }
	    }
	    return resultLists;
	}
	

	public String join() {
		String res = "";
		for(T object : this){
			res+= object.toString();
		}
		return res;
	}
	
	public String join(String sep) {
		String res = "";
		for(int i = 0; i<this.size(); i++){
			if (i+1==this.size()) res += this.get(i).toString();
			else res+= this.get(i).toString()+sep;
		}
		return res;
	}

	public void forEachWithIndex(BiConsumer<T, Integer> c) {
		for(Integer i = 0; i<this.size(); i++){
			c.accept(this.get(i), i);
		}
		
	}
}

