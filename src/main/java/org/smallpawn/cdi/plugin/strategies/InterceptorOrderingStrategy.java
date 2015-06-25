package org.smallpawn.cdi.plugin.strategies;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;

import org.smallpawn.cdi.plugin.InterceptorAwareBeanFactoryPostProcessor;
import org.smallpawn.cdi.plugin.model.InterceptorInfo;

/**
 * Strategy to order interceptors. Clients can implement their own custom ordering strategy.
 * 
 * @author Niklas Schlimm
 *
 */
public interface InterceptorOrderingStrategy {

	/**
	 * Order the interceptors.
	 * 
	 * @param beanFactory the factory that defines the interceptors
	 * @param interceptors the registered interceptors 
	 * @param configuredOrder the configured order of {@link InterceptorAwareBeanFactoryPostProcessor} (if any)
	 * 
	 * @return the ordered list of interceptors
	 */
	@SuppressWarnings("rawtypes")
	List<InterceptorInfo> orderInterceptors(BeanFactory beanFactory, List<InterceptorInfo> interceptors, List<Class> configuredOrder);

}
