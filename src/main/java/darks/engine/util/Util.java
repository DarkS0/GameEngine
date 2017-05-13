package main.java.darks.engine.util;

import java.util.Collection;
import java.util.function.Consumer;

public class Util
{
	public static <T> void forEachClear(Collection<T> collection, Consumer<T> run)
	{
		if (collection.isEmpty()) return;
		collection.forEach(run);
		collection.clear();
	}
}
