package com.ondev.elk;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.Builder;
import lombok.Getter;

@SpringBootApplication
public class SpringDataElasticsearchApplication  implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringDataElasticsearchApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		var valid1 = Set.of(DateRange.builder()
						.startDate(LocalDate.of(2020, Month.JANUARY, 1))
						.endDate(LocalDate.of(2020, Month.MARCH, 1))
						.build(),
				DateRange.builder()
						.startDate(LocalDate.of(2020, Month.AUGUST, 1))
						.endDate(LocalDate.of(2020, Month.DECEMBER, 1))
						.build(),
				DateRange.builder()
						.startDate(LocalDate.of(2020, Month.MARCH, 2))
						.endDate(LocalDate.of(2020, Month.JUNE, 1))
						.build());

		var valid2 = Set.of(DateRange.builder()
						.startDate(LocalDate.of(2020, Month.JANUARY, 1))
						.endDate(LocalDate.of(2020, Month.MARCH, 1))
						.build(),
				DateRange.builder()
						.startDate(LocalDate.of(2020, Month.AUGUST, 1))
						.endDate(LocalDate.of(2020, Month.DECEMBER, 1))
						.build(),
				DateRange.builder()
						.startDate(LocalDate.of(2020, Month.MARCH, 1))
						.build());

		var notValid1 = Set.of(DateRange.builder()
						.startDate(LocalDate.of(2020, Month.JANUARY, 1))
						.endDate(LocalDate.of(2020, Month.MARCH, 1))
						.build(),
				DateRange.builder()
						.startDate(LocalDate.of(2020, Month.AUGUST, 1))
						.endDate(LocalDate.of(2020, Month.DECEMBER, 1))
						.build(),
				DateRange.builder()
						.startDate(LocalDate.of(2020, Month.FEBRUARY, 1))
						.endDate(LocalDate.of(2020, Month.JUNE, 1))
						.build());

		var notValid2 = Set.of(DateRange.builder()
						.startDate(LocalDate.of(2020, Month.JANUARY, 1))
						.endDate(LocalDate.of(2020, Month.MARCH, 1))
						.build(),
				DateRange.builder()
						.startDate(LocalDate.of(2020, Month.AUGUST, 1))
						.endDate(LocalDate.of(2020, Month.DECEMBER, 1))
						.build(),
				DateRange.builder()
						.startDate(LocalDate.of(2020, Month.FEBRUARY, 1))
						.build(),
				DateRange.builder()
						.startDate(LocalDate.of(2020, Month.MARCH, 1))
						.endDate(LocalDate.of(2020, Month.SEPTEMBER, 1))
						.build());

		// Output attendu : true true true false false
		System.out.println(isDateRangesOverlap(Set.of())+" "+
				isDateRangesOverlap(valid1) + " "+ 
				isDateRangesOverlap(valid2) + " " + 
				isDateRangesOverlap(notValid1) + " " + 
				isDateRangesOverlap(notValid2));
	}

	@Getter
	@Builder
	static class DateRange {
		private final LocalDate startDate;

		// Can be null
		private final LocalDate endDate;
	}


	boolean isDateRangesOverlap(final Set<DateRange> dateRanges) {
	    List<DateRange> sortedRanges = new ArrayList<>(dateRanges);
	    sortedRanges.sort(Comparator.comparing(DateRange::getStartDate));
	    DateRange mostRecentRange = null;

	    for (DateRange currentRange : sortedRanges) {
	        if (mostRecentRange != null && currentRange.getStartDate()
	        		.isBefore(Optional.ofNullable(mostRecentRange.getEndDate())
	        				.orElse(mostRecentRange.getStartDate()))) {
	            return false;
	        }
	        mostRecentRange = currentRange;
	    }
	    return true;
	}
}
