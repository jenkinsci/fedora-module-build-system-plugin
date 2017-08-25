node() {
    MBSURL   = "http://localhost:32000"
    USERNAME = "bob"
    CREDID   = "bobs-password"
    MODULE   = "mymodule"
    REV      = "myrev"
    BRANCH   = "mybranch"

    def submission = null
    withCredentials([string(credentialsId: CREDID, variable: 'PASSWORD')]) {
        // make call to MBS
        submission = submitModuleBuildRequest mbsUrl: MBSURL, user: USERNAME, password: PASSWORD, moduleName: MODULE, revision: REV, branch: BRANCH
        echo "my submission id is: " + submission.getId()
    }

    timeout(time: 20, unit: 'SECONDS') {
        def mbsRequest = null
        waitUntil {
            mbsRequest = queryModuleBuildRequest mbsUrl: "http://localhost:32000", moduleRequestId: submission.getId()
            return mbsRequest.isModuleReady()
        }
        echo "Module id: " + mbsRequest.getId() + " is ready"
        echo "${mbsRequest}"
    }

}