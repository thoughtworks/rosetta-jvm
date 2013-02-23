package rosettajvm.tests.acceptance;

import org.jruby.Main;

public class Runner {
    // Doesn't work yet...need to switch to in process testing first.
    public static void main(String[] args) {
        Main.main(new String[] {"classpath:bin/rspec", "-Isrc/test/ruby", "src/test/ruby"});
    }
}