package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import trayecto.Billete;
import trayecto.Ciudad;
import java.time.LocalDate;

/**
 * Tests de caja negra para Billete.
 * "hoy - X" del Excel → fechaViaje = LocalDate.now().plusDays(X)
 * "hoy + X" del Excel → fechaViaje = LocalDate.now().minusDays(X)  [inválido]
 *
 * Trayectos y precios base:
 *   SNT ↔ MAD : 100 €
 *   MAD ↔ BCN : 120 €
 *   SNT ↔ BCN : 150 €
 */
public class BilleteTest {

    @Test
    void tc1_pairwise_SNT_SNT_20_CE13() {
        // origen == destino → IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Billete b = new Billete(Ciudad.SANTANDER, Ciudad.SANTANDER,
                    LocalDate.now().plusDays(3), 20);
            b.calcularPrecioFinal();
        });
    }

    @Test
    void tc2_pairwise_SNT_MAD_60_CE14() {
        // base=100, edad 60→0%, antelación 20d→15% → max=15% → 85.00€
        Billete b = new Billete(Ciudad.SANTANDER, Ciudad.MADRID,
                LocalDate.now().plusDays(20), 60);
        assertEquals(85.0, b.calcularPrecioFinal());
    }

    @Test
    void tc3_pairwise_SNT_BCN_70_CE15() {
        // base=150, edad 70→40%, antelación 40d→25% → max=40% → 90.00€
        Billete b = new Billete(Ciudad.SANTANDER, Ciudad.BARCELONA,
                LocalDate.now().plusDays(40), 70);
        assertEquals(90.0, b.calcularPrecioFinal());
    }

    @Test
    void tc4_pairwise_MAD_SNT_60_CE15() {
        // base=100, edad 60→0%, antelación 40d→25% → max=25% → 75.00€
        Billete b = new Billete(Ciudad.MADRID, Ciudad.SANTANDER,
                LocalDate.now().plusDays(40), 60);
        assertEquals(75.0, b.calcularPrecioFinal());
    }

    @Test
    void tc5_pairwise_MAD_MAD_70_CE13() {
        // origen == destino → IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Billete b = new Billete(Ciudad.MADRID, Ciudad.MADRID,
                    LocalDate.now().plusDays(3), 70);
            b.calcularPrecioFinal();
        });
    }

    @Test
    void tc6_pairwise_MAD_BCN_20_CE14() {
        // base=120, edad 20→30%, antelación 20d→15% → max=30% → 84.00€
        Billete b = new Billete(Ciudad.MADRID, Ciudad.BARCELONA,
                LocalDate.now().plusDays(20), 20);
        assertEquals(84.0, b.calcularPrecioFinal());
    }

    @Test
    void tc7_pairwise_BCN_SNT_70_CE14() {
        // base=150, edad 70→40%, antelación 20d→15% → max=40% → 90.00€
        Billete b = new Billete(Ciudad.BARCELONA, Ciudad.SANTANDER,
                LocalDate.now().plusDays(20), 70);
        assertEquals(90.0, b.calcularPrecioFinal());
    }

    @Test
    void tc8_pairwise_BCN_MAD_20_CE15() {
        // base=120, edad 20→30%, antelación 40d→25% → max=30% → 84.00€
        Billete b = new Billete(Ciudad.BARCELONA, Ciudad.MADRID,
                LocalDate.now().plusDays(40), 20);
        assertEquals(84.0, b.calcularPrecioFinal());
    }

    @Test
    void tc9_pairwise_BCN_BCN_60_CE13() {
        // origen == destino → IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Billete b = new Billete(Ciudad.BARCELONA, Ciudad.BARCELONA,
                    LocalDate.now().plusDays(3), 60);
            b.calcularPrecioFinal();
        });
    }

    @Test
    void tc10_pairwise_origenInvalido_CE4() {
        // CE4: ciudad origen inválida (ZGZ no existe en el enum → se usa null como proxy)
        // obtenerPrecioBase() no encuentra trayecto → IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Billete b = new Billete(null, Ciudad.SANTANDER,
                    LocalDate.now().plusDays(3), 20);
            b.calcularPrecioFinal();
        });
    }

    @Test
    void tc11_pairwise_destinoInvalido_CE8() {
        // CE8: ciudad destino inválida (ZGZ no existe en el enum → se usa null como proxy)
        // obtenerPrecioBase() no encuentra trayecto → IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Billete b = new Billete(Ciudad.SANTANDER, null,
                    LocalDate.now().plusDays(3), 20);
            b.calcularPrecioFinal();
        });
    }

    @Test
    void tc12_pairwise_edadNegativa() {
        // CE12: edad=-10 → IllegalArgumentException en constructor
        assertThrows(IllegalArgumentException.class, () -> {
            Billete b = new Billete(Ciudad.SANTANDER, Ciudad.MADRID,
                    LocalDate.now().plusDays(3), -10);
            b.calcularPrecioFinal();
        });
    }

    @Test
    void tc13_pairwise_fechaPasada() {
        // CE16: fecha pasada (now-5) → IllegalArgumentException en constructor
        assertThrows(IllegalArgumentException.class, () -> {
            Billete b = new Billete(Ciudad.SANTANDER, Ciudad.MADRID,
                    LocalDate.now().minusDays(5), 20);
            b.calcularPrecioFinal();
        });
    }
}
