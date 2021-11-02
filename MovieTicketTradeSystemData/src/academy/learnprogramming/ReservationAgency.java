package academy.learnprogramming;

public class ReservationAgency {
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        boolean discountable = chekDiscountable(screening);
        Money fee = screening.calculateFee(screening, discountable, audienceCount);
        return createReservation(screening, customer, audienceCount, fee);
    }

    private boolean checkDiscountable(Screening screening) {
        return screening.getMovie().getDiscountConditions().stream().anyMatch(condition -> condition.isDiscountable(screening));
    }





    private Money calculateFee(Screening screening, boolean discountable, int audienceCount) {
        if (discountable) {
            return screening.getMovie().getFee().minus(calculateDiscountFee(screening.getMovie())).times(audienceCount);
        }

        return screening.getMovie().getFee().times(audienceCount);
    }

    private Money calculateDiscountFee(Movie movie) {
        switch (movie.getMovieType()) {
            case AMOUNT_DISCOUNT:
                return calculateAmountDiscountFee(movie);
            case PERCENT_DISCOUNT:
                return calculatePercentDiscountFee(movie);
            case NONE_DISCOUNT:
                return calculateNoneDiscountFee(movie);
        }

        throw new IllegalArgumentException();
    }

    private Money calculateAmountDiscountFee(Movie movie) {
        return movie.getDiscountAmount();
    }

    private Money calculatePercentDiscountFee(Movie movie) {
        return movie.getFee().times(movie.getDiscountPercent());
    }

    private Money calculateNoneDiscountFee(Movie movie) {
        return Money.ZERO;
    }

    private Reservation createReservation(Screening screening, Customer customer, int audienceCount, Money fee) {
        return new Reservation(customer, screening, fee, audienceCount);
    }


}
