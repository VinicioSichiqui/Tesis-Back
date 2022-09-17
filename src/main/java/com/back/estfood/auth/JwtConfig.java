package com.back.estfood.auth;

/*
 * Configuracion firma publica y privada para el token
 * 
 * importante: Cambiar las claves al momento de lanzar a aproduccion por seguridad
 * 
 * descargar :
 * 
 * https://slproweb.com/products/Win32OpenSSL.html  version completa no ligth
 * 
 * instalar y entrar a ala carpeta y ejecutar el .exe
 * 
 * openssl genrsa -out jwt.pem
 * 
 *   llave privada
 * openssl genrsa -in jwt.pem
 *   LLave publica
 * openssl genrsa -in jwt.pem -pubout
 * */

public class JwtConfig {
	// Sistema criptografico de llave publica y privada
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEowIBAAKCAQEA6+y493D8xp/xVRhVs1yiXAspf1Rvi04EBgPYsHVWYAYjbd/X\r\n"
			+ "iZBAmLd9B3249OFeznX5ZGKRjsa49qXj3k/j3mok2q5JOURBgHr1KejthaQ59FOM\r\n"
			+ "zP6bbfdttR+GJfTQkdCHiOeqDNFckBO2N6Smn1V5wPive6e1BjjYFC80LD2kvrt2\r\n"
			+ "ByiQTvyBs2l3WL0O4DZVN/5038Pbry2iEAjdtMrCnKV3S/RHpi9rs/Q6QDYsFrgQ\r\n"
			+ "t0Rdmwbhs2iAjGHPaeSexWH6j5EufZIyo0Y8tIVaynvM3cEOrKqMjXb7MBGOPQwM\r\n"
			+ "+xMJDuoCYVSmgfSHdLafhxc7/XlNzTYnHb7hKwIDAQABAoIBAEiMVeC5XPHTU0oA\r\n"
			+ "nrH2JXM6d3tRQq/KwM/8tNCmdHY4zQJHmCcUNdkAa7E3Bv4+o4HHo3YsHWiTUWN/\r\n"
			+ "ZdACLydA6q5Yc+npRF828Fb/6rJy6uNoId8T2EX7lT/iPsVDQDPmtRbzbkZcxyMC\r\n"
			+ "/MCloBtVFIizsHrM+oy8RC4IyflkMC7xXxI6iWqol/uvrT4Ob0X9B+7NDaKQsOGa\r\n"
			+ "cp4aMV+O8nyQEyKB3+DVRthgSpDUdamWHRhkzyKjaru/t3BZuIy7jtGVk3IEujiN\r\n"
			+ "DhWimubIWCygHUBFx6Kcags4wbcpTDtnkv6hy2ZJod5dWLiZLQ+MmaMFrNmsc1CF\r\n"
			+ "AsmpWVkCgYEA+gEOb41bPlPnLkMxbrdj9M+v/kqdensgJMsmW/Ob80DkW9aRRC7+\r\n"
			+ "/1lV1MjsZ5UzG/JuxOjE+MiIl5JqZW1ppOwCJnNuSAoKDCxIZSE50NrbgQAU9uGq\r\n"
			+ "DtgBHFBy2Og3phevLtpAnjRIoSGV6Yd8iRF5ig7jlgCI8qPyJAwsqV8CgYEA8ZU5\r\n"
			+ "GWH2vGcOotcT9WbOCjF/V1++FZG+O3QjRKFp6XPOmIYztN1049zY4dtAfa9BH29z\r\n"
			+ "psCdWmM1pmzsox3WOa21m/zjifq1j1Eh4eZkXmse0aLD5fkAroCUzPx9A8hATJ3J\r\n"
			+ "uc7nc+987vl1Jk+XdoJQY8mIsgagmGBhC/b7f7UCgYBRoIKsHYVLJm33Bu1e7vXt\r\n"
			+ "+tnFt2sPkKAAI2xU3YdvtyJYcghgMNAFY4/IuAmoEGjtmQxdsnqNmDww8RdYIo85\r\n"
			+ "z+0rzMiXMN/y9Yp1qpGMBclhpzsxGKFD03cZXZMCC2AQFO+X5p7uiLemFKcz5o7c\r\n"
			+ "2h3K2+2nDmmof6sR5f+3FQKBgCXC8X3qNisU4NDLVa9SNnqZahI6QPgWI+ofKTQd\r\n"
			+ "FvUKyQxGTebDhvXnG2+SWe5Lap6DSUyTZwUt5MDy3bpRe57gbDbJvLdgnOieAfHR\r\n"
			+ "lXa8v8B89KoJ1fVw29F+1fu1XXecYl/M25jfomOoUot+fWpkzDQM2HUbXBb1WbYJ\r\n"
			+ "ER7RAoGBAI5/B6362buZRPDnY1IX/f0vSg0SXKpNu3DYApmyCBg6wE8e1irNedGE\r\n"
			+ "8YW/yWnXhyOQrMcGC2HMMKa/Pt9wdrXkZRvO0RjBlha1nNqVg8gYZdxQVzOc0ELn\r\n"
			+ "uqFIPI7sbCD62wRLPDSVYBDN/WJI9v9m9E8aF7SSVRGPTU/gUz/y\r\n"
			+ "-----END RSA PRIVATE KEY-----";
	public static final String RSA_PUBLICA =  "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6+y493D8xp/xVRhVs1yi\r\n"
			+ "XAspf1Rvi04EBgPYsHVWYAYjbd/XiZBAmLd9B3249OFeznX5ZGKRjsa49qXj3k/j\r\n"
			+ "3mok2q5JOURBgHr1KejthaQ59FOMzP6bbfdttR+GJfTQkdCHiOeqDNFckBO2N6Sm\r\n"
			+ "n1V5wPive6e1BjjYFC80LD2kvrt2ByiQTvyBs2l3WL0O4DZVN/5038Pbry2iEAjd\r\n"
			+ "tMrCnKV3S/RHpi9rs/Q6QDYsFrgQt0Rdmwbhs2iAjGHPaeSexWH6j5EufZIyo0Y8\r\n"
			+ "tIVaynvM3cEOrKqMjXb7MBGOPQwM+xMJDuoCYVSmgfSHdLafhxc7/XlNzTYnHb7h\r\n"
			+ "KwIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";
}
