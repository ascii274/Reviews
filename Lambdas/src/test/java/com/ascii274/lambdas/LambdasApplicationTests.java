package com.ascii274.lambdas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Consumer;
import java.util.stream.Stream;

@SpringBootTest
class LambdasApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void whenNamesPresentConsumeAll(){
		Consumer<String> printConsumer = t->System.out.println(t);
		Stream<String> cities = Stream.of("Sydney","Dhaka", "Barcelona","Paris");
		cities.forEach(printConsumer);
	}



}
