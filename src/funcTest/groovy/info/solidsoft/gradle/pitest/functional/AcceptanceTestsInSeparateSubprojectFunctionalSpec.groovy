package info.solidsoft.gradle.pitest.functional

class AcceptanceTestsInSeparateSubprojectFunctionalSpec extends AbstractPitestFunctionalSpec {

    def "should mutate production code in another subproject"() {
        given:
            buildFile << """
                apply plugin: 'com.android.library'
                apply plugin: 'pl.droidsonroids.pitest'

                android {
                    buildToolsVersion '24.0.0'
                    compileSdkVersion 24
                    defaultConfig {
                        minSdkVersion 10
                        targetSdkVersion 24
                    }
                }
            """
            copyResources("testProjects/multiproject", "")
        when:
            def result = runTasksSuccessfully('pitestRelease')
        then:
            result.wasExecuted(':itest:pitestRelease')
            result.getStandardOutput().contains('Generated 2 mutations Killed 2 (100%)')
            result.getStandardOutput().contains('Generated 2 mutations Killed 1 (50%)')
    }
}
