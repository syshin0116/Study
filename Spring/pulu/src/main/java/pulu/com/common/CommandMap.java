package pulu.com.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// 여기서 중요한점은 절대로 Map을 상속받으면 안된다.
// Map을 상속받게 되면, 우리가 작성할 ArgumentResolver를 거치지 않게 되니 주의하자.
public class CommandMap {
	
	Map<String, Object> map = new HashMap<String, Object>();
	
	
	public Object get(String key){
		return map.get(key);
	}
	
	// map에 key와 value 삽입
	public void put(String key, Object value){
		map.put(key, value);
	}
	
	// map에 key를 가져와 삭제
	public Object remove(String key){
		return map.remove(key);
	}
	
	// map에 해당 key를 포함하는지 체크
	public boolean containsKey(String key){
		return map.containsKey(key);
	}
	
	public boolean containsValue(Object value){
		return map.containsValue(value);
	}
	
	// map에 모든 객체 삭제
	public void clear(){
		map.clear();
	}
	
	// key와 value를 하나의 entry로 묶는 역할
	public Set<Entry<String, Object>> entrySet(){
		return map.entrySet();
	}
	
	// map 안에 있는 key를 set으로 가져오는 역할
	public Set<String> keySet(){
		return map.keySet();
	}
	
	public boolean isEmpty(){
		return map.isEmpty();
	}
	
	// String과 Object를 기준 자신을 포함한 하위 클래스를 타입 지정
	public void putAll(Map<? extends String, ?extends Object> m){
		map.putAll(m);
	}
	
	// CommandMap을 Map과 같이 사용할 수 있게 getMap() 메서드 추가
	public Map<String,Object> getMap(){
		return map;
	}
}
