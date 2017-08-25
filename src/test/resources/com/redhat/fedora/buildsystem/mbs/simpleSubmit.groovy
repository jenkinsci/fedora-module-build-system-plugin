node() {

    MBSURL   = "http://localhost:32000"
    USERNAME = "bob"
    CREDID   = "bobs-password"
    MODULE   = "mymodule"
    REV      = "myrev"
    BRANCH   = "mybranch"

    withCredentials([string(credentialsId: CREDID, variable: 'PASSWORD')]) {

        // make call to MBS
        submission = submitModuleBuildRequest mbsUrl: MBSURL, user: USERNAME, password: PASSWORD, moduleName: MODULE, revision: REV, branch: BRANCH

        echo "my submission id is: " + submission.getId()

        sleep 10
    }

}