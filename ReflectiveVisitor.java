import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

interface ReflectiveVisitor {
	void visit(Object o) throws Exception;
}

class ConcreteVisitor implements ReflectiveVisitor {

	@Override
	public void visit(Object o) throws Exception {
		Method downPolymorphic = getPolymorphicMethod(o.getClass());
		if (downPolymorphic == null) {
			defaultVisit(o);
		} else {
			downPolymorphic.invoke(this, new Object[] { o });
		}

	}

	public void defaultVisit(Object o) {
		System.out.println(o.toString());
	}

	Method getPolymorphicMethod(Class c) {
		Method m = null;
		Class newC = c;
		while (newC != Object.class) {
			try {
				return this.getClass().getMethod("visit", newC);

			} catch (NoSuchMethodException e) {
				newC = newC.getSuperclass();

			}

		}
		if (newC == Object.class) {
			Class[] interfaces = c.getInterfaces();
			for (Class c1 : interfaces) {
				try {
					return this.getClass().getMethod("visit", c1);

				}

				catch (NoSuchMethodException e) {

				}

			}
		}
		return m;

	}

	void visit(Book b) {

	}

	void visit(Pen b) {

	}

}
