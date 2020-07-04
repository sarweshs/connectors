package org.serviceinterface;

import java.util.List;

public interface IRule<T, R> {

	List<T> getRulesOutput(R r);
}
