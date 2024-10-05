package de.grademanager

import de.grademanager.core.domain.ext.formatGrade
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FormatGradeDecimalPlacesTest {

    @Test
    fun `test format grade without decimal places`() {
        assertEquals("1", 1.000.formatGrade())
    }

    @Test
    fun `test format grade with one decimal place`() {
        assertEquals("1.1", 1.100.formatGrade())
    }

    @Test
    fun `test format grade wit two decimal places`() {
        assertEquals("1.33", 1.333.formatGrade())
    }

}