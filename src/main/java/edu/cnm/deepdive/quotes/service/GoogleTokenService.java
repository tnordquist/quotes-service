package edu.cnm.deepdive.quotes.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

@Component
public class GoogleTokenService implements ResourceServerTokenServices {

  private static final String VERIFICATION_FAILURE =
      "Provided token could not be verified; check for stale credentials";
  private static final String ROLE_PATTERN = "ROLE_%s";

  private final String clientId;
  private final AccessTokenConverter converter;
  // TODO Reference UserService.

  @Autowired
  public GoogleTokenService(@Value("${oauth.clientId}") String clientId) {
    this.clientId = clientId;
    converter = new DefaultAccessTokenConverter();
  }


  @Override
  public OAuth2Authentication loadAuthentication(String token)
      throws AuthenticationException, InvalidTokenException {
    HttpTransport transport = new NetHttpTransport();
    JacksonFactory jsonFactory = new JacksonFactory();
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
        .setAudience(Collections.singletonList(clientId))
        .build();
    GoogleIdToken idToken = null;
    try {
      idToken = verifier.verify(token);
    } catch (GeneralSecurityException | IOException e) {
      throw new RuntimeException(e);
    }
    if (idToken != null) {
      Payload payload = idToken.getPayload();
      String oauthKey = payload.getSubject();
      String displayName = (String) payload.get("name");
      // TODO Extract any additional required info from payload.
      // TODO Request user from UserService.
      return null; // TODO Replace with actual value.
    } else {
      throw new InvalidTokenException(VERIFICATION_FAILURE);
    }
  }

  @Override
  public OAuth2AccessToken readAccessToken(String s) {
    return null;
  }

}
