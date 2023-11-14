package com.example.problema_1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NumeroEntero {
    private BigDecimal valor;
    private StringBuilder procedimiento;
    private List<BigDecimal> cocientes;
    private List<BigDecimal> residuos;

    public NumeroEntero(String numero) {
        this.valor = new BigDecimal(numero);
        this.procedimiento = new StringBuilder();
        this.cocientes = new ArrayList<>();
        this.residuos = new ArrayList<>();
    }

    public StringBuilder dividir(NumeroEntero otroNumero) {
        if (otroNumero.esCero()) {
            throw new ArithmeticException("No se puede dividir por cero.");
        }

        BigDecimal divisor = otroNumero.getValor();
        BigDecimal cociente = this.valor.divide(divisor, 15, BigDecimal.ROUND_DOWN);
        BigDecimal residuo = this.valor.remainder(divisor);

        // Agregar el primer cociente y residuo a las listas
        cocientes.add(cociente);
        residuos.add(residuo);

        // Mostrar el primer residuo
        procedimiento.append("Resultado: ").append(cociente.intValue());

        // Mostrar residuos adicionales en un bucle
        while (residuo.compareTo(BigDecimal.ZERO) != 0) {
            procedimiento.append("\nCon Residuo: ").append(residuo);

            // Agregar el cociente y residuo actual a las listas
            this.valor = residuo.multiply(BigDecimal.TEN); // Mover el residuo al siguiente decimal
            cociente = this.valor.divide(divisor, 15, BigDecimal.ROUND_DOWN);
            residuo = this.valor.remainder(divisor);
            cocientes.add(cociente);
            residuos.add(residuo);
        }

        return procedimiento;
    }

    public List<BigDecimal> getCocientes() {
        return cocientes;
    }

    public List<BigDecimal> getResiduos() {
        return residuos;
    }

    public boolean esCero() {
        return this.valor.compareTo(BigDecimal.ZERO) == 0;
    }

    public BigDecimal getValor() {
        return this.valor;
    }
}
