package scripts;

import java.util.Random;

public class ReactionGenerator {
    // Do Stuff
    // Realize next action needs to be done
  //  wait(ReactionGenerator.nextReactionTime(200, 50, 0.007, 0.2, 100, 1800));
    // Do next action


    private static ReactionGenerator rxnGenerator;

    public static ReactionGenerator get() {
        return rxnGenerator == null ? rxnGenerator = new ReactionGenerator() : rxnGenerator;
    }

    Random randomGen = new Random();

    public double nextGaussian(double mu, double sigma) {
        return mu + randomGen.nextGaussian() * sigma;
    }

    public double nextExponential(double lambda) {
        return -Math.log(1 - randomGen.nextDouble()) / lambda;
    }

    public double nextExGaussian(double mu, double sigma, double lambda) {
        return nextGaussian(mu, sigma) + nextExponential(lambda);
    }

    public double nextUniform(double minimum, double maximum) {
        return randomGen.nextDouble() * (maximum - minimum) + minimum;
    }

    public double nextReactionTime(double mu, double sigma, double lambda, double p, double minimum, double maximum) {
        if (randomGen.nextDouble() < p) {
            return nextUniform(minimum, maximum);
        }

        double exGaussian = nextExGaussian(mu, sigma, lambda);
        if (exGaussian < minimum || exGaussian > maximum) {
            return nextReactionTime(mu, sigma, lambda, p, minimum, maximum);
        }

        return exGaussian;
    }
}

