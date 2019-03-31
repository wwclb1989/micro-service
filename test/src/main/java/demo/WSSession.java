package demo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WSSession implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String token;				// 存储返回用户端的token
	private Set<String> permissions;		// 存储有权限的url列表
	private Map<String, Object> attributes;	// 存储Session共享变量
	
	public WSSession(String token) {
		this.token = token;
		permissions = new HashSet<>();
		attributes = new HashMap<>();
	}
	
	public String getToken() {
		return token;
	}
	
	public void updatePermissions(Set<String> permissions) {
		this.permissions.clear();
		this.permissions.addAll(permissions);
	}
	
	public boolean hasPermission(String url) {
		if (permissions.contains(url)) {
			return true;
		}
		return false;
	}
	
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
}
