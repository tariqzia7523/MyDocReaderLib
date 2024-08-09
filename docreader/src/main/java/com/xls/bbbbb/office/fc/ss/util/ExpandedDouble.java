package com.xls.bbbbb.office.fc.ss.util;

import static com.xls.bbbbb.office.fc.ss.util.IEEEDouble.FRAC_ASSUMED_HIGH_BIT;
import static com.xls.bbbbb.office.fc.ss.util.IEEEDouble.FRAC_MASK;

import java.math.BigInteger;

/**
 * Represents a 64 bit IEEE double quantity expressed with both decimal and binary exponents
 * Does not handle negative numbers or zero
 * <p/>
 * The value of a {@link ExpandedDouble} is given by<br/>
 * <tt> a &times; 2<sup>b</sup></tt>
 * <br/>
 * where:<br/>
 *
 * <tt>a</tt> = <i>significand</i><br/>
 * <tt>b</tt> = <i>binaryExponent</i> - bitLength(significand) + 1<br/>
 *
 * @author Josh Micich
 */
final class ExpandedDouble {
	private static final BigInteger BI_FRAC_MASK = BigInteger.valueOf(FRAC_MASK);
	private static final BigInteger BI_IMPLIED_FRAC_MSB = BigInteger.valueOf(FRAC_ASSUMED_HIGH_BIT);

	private static BigInteger getFrac(long rawBits) {
		return BigInteger.valueOf(rawBits).and(BI_FRAC_MASK).or(BI_IMPLIED_FRAC_MSB).shiftLeft(11);
	}


	public static ExpandedDouble fromRawBitsAndExponent(long rawBits, int exp) {
		return new ExpandedDouble(getFrac(rawBits), exp);
	}

	/**
	 * Always 64 bits long (MSB, bit-63 is '1')
	 */
	private final BigInteger _significand;
	private final int _binaryExponent;

	public ExpandedDouble(long rawBits) {
		int biasedExp = (int) (rawBits >> 52);
		if (biasedExp == 0) {
			// sub-normal numbers
			BigInteger frac = BigInteger.valueOf(rawBits).and(BI_FRAC_MASK);
			int expAdj = 64 - frac.bitLength();
			_significand = frac.shiftLeft(expAdj);
			_binaryExponent = (biasedExp & 0x07FF) - 1023 - expAdj;
		} else {
			BigInteger frac = getFrac(rawBits);
			_significand = frac;
			_binaryExponent = (biasedExp & 0x07FF) - 1023;
		}
	}

	ExpandedDouble(BigInteger frac, int binaryExp) {
		if (frac.bitLength() != 64) {
			throw new IllegalArgumentException("bad bit length");
		}
		_significand = frac;
		_binaryExponent = binaryExp;
	}


	/**
	 * Convert to an equivalent {@link NormalisedDecimal} representation having 15 decimal digits of precision in the
	 * non-fractional bits of the significand.
	 */
	public NormalisedDecimal normaliseBaseTen() {
		return NormalisedDecimal.create(_significand, _binaryExponent);
	}

	/**
	 * @return the number of non-fractional bits after the MSB of the significand
	 */
	public int getBinaryExponent() {
		return _binaryExponent;
	}

	public BigInteger getSignificand() {
		return _significand;
	}
}
