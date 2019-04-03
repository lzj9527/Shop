package android.extend.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

public class SSLSocketFactoryImpl extends SSLSocketFactory
{
	SSLContext sslContext = SSLContext.getInstance(SSLSocketFactory.TLS);

	public SSLSocketFactoryImpl(KeyStore trustStore) throws NoSuchAlgorithmException, KeyManagementException,
			KeyStoreException, UnrecoverableKeyException
	{
		super(trustStore);
		TrustManager tm = new X509TrustManager()
		{
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException
			{
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException
			{
			}
		};

		setHostnameVerifier(ALLOW_ALL_HOSTNAME_VERIFIER);
		sslContext.init(null, new TrustManager[] { tm }, null);
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
			UnknownHostException
	{
		return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	}

	@Override
	public Socket createSocket() throws IOException
	{
		return sslContext.getSocketFactory().createSocket();
	}
}
