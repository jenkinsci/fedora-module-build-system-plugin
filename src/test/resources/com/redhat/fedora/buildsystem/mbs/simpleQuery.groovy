import hudson.util.Secret

node() {
    waitUntil {
        query = queryModuleBuildRequest mbsUrl: "http://localhost:32000"
        return query.isModuleReady(1)
    }
    echo "Module id: 1 is ready"
}