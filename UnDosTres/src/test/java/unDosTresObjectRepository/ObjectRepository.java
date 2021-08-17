package unDosTresObjectRepository;

import org.openqa.selenium.By;

public class ObjectRepository {

	// xpath
	
	// Home page
	public static final By operatorColumn = By.xpath("//label[contains(text(),'Operador')][1]");
	public static final By mobileNumColumn = By.xpath("//input[@data-qa='celular-mobile']");
	public static final By cellularAmountColumn = By.xpath("//input[@data-qa='celular-amount']");
	
	// Payment page
	public static final By Tarjeta = By.xpath("//span[@id='cardGly']");
	public static final By radioButtonTarjeta = By.xpath("//span[contains(text(),'Usar nueva tarjeta')]");
	public static final By pagarConTarjeta = By.xpath("//div[@id='new-card-button-desktop']//span[contains(text(),'Pagar con Tarjeta')]");
	
}
