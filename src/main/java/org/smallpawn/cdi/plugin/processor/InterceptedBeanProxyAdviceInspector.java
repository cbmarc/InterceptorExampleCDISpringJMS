package org.smallpawn.cdi.plugin.processor;

/**
 * Infrastructure Interface to retrieve the {@link InterceptedBeanProxyAdvice} from an intercepted bean.
 * 
 * @author Niklas Schlimm
 *
 */
public interface InterceptedBeanProxyAdviceInspector {

	Object getInterceptorTarget();
	InterceptedBeanProxyAdvice getInterceptor();
	
}
