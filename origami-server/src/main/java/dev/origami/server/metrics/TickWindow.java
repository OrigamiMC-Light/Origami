package dev.origami.server.metrics;

/**
 * Fixed-size ring buffer of tick durations (nanoseconds).
 * Thread-safe, allocation-free after construction — safe to call every tick.
 */
public final class TickWindow {
    private final long[] samples;
    private int index;
    private int count;

    public TickWindow(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be positive");
        }
        this.samples = new long[size];
    }

    public synchronized void record(long durationNanos) {
        this.samples[this.index] = durationNanos;
        this.index = (this.index + 1) % this.samples.length;
        if (this.count < this.samples.length) {
            this.count++;
        }
    }

    public synchronized double averageMillis() {
        if (this.count == 0) {
            return 0.0;
        }
        long sum = 0;
        for (int i = 0; i < this.count; i++) {
            sum += this.samples[i];
        }
        return (sum / (double) this.count) / 1_000_000.0;
    }

    public double tps() {
        double mspt = this.averageMillis();
        if (mspt <= 0.0) {
            return 20.0;
        }
        return Math.min(20.0, 1000.0 / mspt);
    }

    public synchronized int sampleCount() {
        return this.count;
    }
}