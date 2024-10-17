package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class VariableStore {
    private HashMap<String, Double> variables;

    public VariableStore() {
        this.variables = new HashMap<>();
    }

    public void setVariable(String name, double value) {
        variables.put(name, value);
    }

    public double getVariable(String name) throws Exception {
        if (!variables.containsKey(name)) {
            throw new Exception("Переменная " + name + " не найдена.");
        }
        return variables.get(name);
    }

    public Set<String> getVariables() {
        return new HashSet<>(variables.keySet());
    }
}
