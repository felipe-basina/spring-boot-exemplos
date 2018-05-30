package br.com.becommerce.security.api.component;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.becommerce.security.api.model.TokenCredential;

@Component
public class UserValidation {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static Map<String, String> usersMap = new HashMap<>();

	public UserValidation() {
		usersMap.put("user-app1", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoiam9obi5kb2UiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTI3NzI3MzU3LCJhdXRob3JpdGllcyI6WyJTVEFOREFSRF9VU0VSIl0sImp0aSI6ImY3ZDUwNjNmLTQyZWItNDBlOS05NjNjLTYxYTI4MjJhZjgzNyIsImNsaWVudF9pZCI6InRlc3Rqd3RjbGllbnRpZCJ9.zqaB-nSGRtHPCRfd5KlBkp8W_VOP3RlESRMmsbahOGY");
		usersMap.put("user-app2", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoiam9obi5kb2UiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTI3NzI3MzU3LCJhdXRob3JpdGllcyI6WyJTVEFOREFSRF9VU0VSIl0sImp0aSI6ImY3ZDUwNjNmLTQyZWItNDBlOS05NjNjLTYxYTI4MjJhZjgzNyIsImNsaWVudF9pZCI6InRlc3Rqd3RjbGllbnRpZCJ9.zqaB-nSGRtHPCRfd5KlBkp8W_VOP3RlESRMmsbahOGZ");
	}
	
	public boolean isValid(TokenCredential tokenCredential) {
		try {

			return usersMap.get(tokenCredential.getUser()).equals(tokenCredential.getToken());

		} catch (Exception ex) {
			logger.error("User {} not found. {}", tokenCredential.getUser(), ex.getMessage());
			return false;
		}
	}
	
}
