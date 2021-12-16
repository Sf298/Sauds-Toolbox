package sauds.toolbox.idgenerator;

import java.math.BigInteger;

public class SemiRandomIdGenerator {

    private final long maxVal;
    private final long prime1;
    private final long prime2;
    private long currentVal;

    public SemiRandomIdGenerator(long maxVal) {
        if (maxVal <= 3) {
            throw new IllegalArgumentException("maxVal must be greater than 3");
        }
        this.maxVal = maxVal;
        this.currentVal = (long) (Math.random() * maxVal);
        this.prime1 = nextPrime(maxVal / 3);
        this.prime2 = nextPrime(maxVal);
    }

    public long next() {
        do {
            currentVal = (currentVal + prime1) % prime2;
        } while (currentVal >= maxVal);
        return currentVal;
    }

    private long nextPrime(long lowerBound) {
        while (!isPrime(lowerBound)) {
            lowerBound++;
        }
        return lowerBound;
    }
    private boolean isPrime(long l) {
        if (!BigInteger.valueOf(l).isProbablePrime(15)) {
            return false;
        }

        if ((l & 1L) == 0) {
            return false;
        }

        long sqrt = (long) Math.sqrt(l) + 1;
        for (int i = 3; i < sqrt; i += 2) {
            if (l % i == 0) {
                return false;
            }
        }

        return true;
    }

}
