package unDosTresObjectRepository;

import org.openqa.selenium.By;

public class ObjectRepository {

	public static final By operatorColumn = By.xpath("//label[contains(text(),'Operador')][1]");
	public static final By mobileNumColumn = By.xpath("//input[@data-qa='celular-mobile']");
	public static final By cellularAmountColumn = By.xpath("//input[@data-qa='celular-amount']");
	public static final By Tarjeta = By.xpath("//span[@id='cardGly']");
	public static final By radioButtonTarjeta = By.xpath("//span[contains(text(),'Usar nueva tarjeta')]");


}