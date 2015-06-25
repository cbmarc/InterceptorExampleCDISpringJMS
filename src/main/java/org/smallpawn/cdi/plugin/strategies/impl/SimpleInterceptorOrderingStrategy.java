package org.smallpawn.cdi.plugin.strategies.impl;

import org.smallpawn.cdi.plugin.InterceptorAwareBeanFactoryPostProcessor;
import org.smallpawn.cdi.plugin.model.InterceptorInfo;
import org.smallpawn.cdi.plugin.strategies.InterceptorOrderingStrategy;
import org.springframework.beans.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple ordering strategy that applies the configured order of {@link InterceptorAwareBeanFactoryPostProcessor}.
 * 
 * @author Niklas Schlimm
 *
 */
public class SimpleInterceptorOrderingStrategy implements InterceptorOrderingStrategy {

	@SuppressWarnings("rawtypes")
	@Override
	public List<InterceptorInfo> orderInterceptors(BeanFactory beanFactory, List<InterceptorInfo> interceptors, List<Class> configuredOrder) {
		if (configuredOrder!=null && configuredOrder.size() > 0) {
			return sortInterceptors(configuredOrder, interceptors);
		} else {
			return interceptors;
		}
	}

	@SuppressWarnings("rawtypes")
	private List<InterceptorInfo> sortInterceptors(List<Class> configuredOrder, List<InterceptorInfo> unsortedInterceptors) {
		List<InterceptorInfo> sortedInterceptors = new ArrayList<InterceptorInfo>();
		for (Class interceptorClass : configuredOrder) {
			for (InterceptorInfo interceptorInfo : unsortedInterceptors) {
				if (interceptorInfo.getBeanDefinitionHolder().getBeanDefinition().getBeanClassName().equals(interceptorClass.getName())) {
					sortedInterceptors.add(interceptorInfo);
				}
			}
		}
		List<InterceptorInfo> unorderedInterceptors = new ArrayList<InterceptorInfo>();
		for (InterceptorInfo interceptorInfo : unsortedInterceptors) {
			if (!sortedInterceptors.contains(interceptorInfo)) {
				unorderedInterceptors.add(interceptorInfo);
			}
		}
		sortedInterceptors.addAll(unorderedInterceptors);
		return sortedInterceptors;
	}

}
