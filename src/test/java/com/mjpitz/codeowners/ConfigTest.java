package com.mjpitz.codeowners;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ConfigTest {
    @Test
    public void test() throws URISyntaxException, IOException {
        final Config config = Config.parse(Files.lines(
                Paths.get(ClassLoader.getSystemResource("TEST_CODEOWNERS").toURI())
        ));

        assertEquals(11, config.rules.size());
        assertEquals(3, config.reviewerCount);

        assertEquals(3, config.ownersFor("/apps/github").size());
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "@js-owner", "@octocat"), config.ownersFor("/apps/main.js"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "docs@example.com", "@octocat"), config.ownersFor("/apps/main.go"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "@octocat", "@doctocat"), config.ownersFor("/scripts/deploy.sh"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "@doctocat", "docs@example.com"), config.ownersFor("/docs/README.md"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "@octocat", "@js-owner"), config.ownersFor("/internal/apps/main.js"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "@octocat", "docs@example.com"), config.ownersFor("/internal/apps/main.go"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2"), config.ownersFor("/internal/docs/README.md"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "@doctocat"), config.ownersFor("/build/logs/out.json"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "@octo-org/octocats"), config.ownersFor("/internal/lib/lib.txt"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "docs@example.com"), config.ownersFor("/internal/lib/lib.go"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "@js-owner"), config.ownersFor("/internal/lib/index.js"));
        assertEquals(Sets.newHashSet("@global-owner1", "@global-owner2", "@global-owner1", "@global-owner2"), config.ownersFor("Jenkinsfile"));
    }
}
