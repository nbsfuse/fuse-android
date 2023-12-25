
# Copyright 2023 Breautek 

# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at

#     http://www.apache.org/licenses/LICENSE-2.0

# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

source ../build-tools/DirectoryTools.sh
source ../build-tools/assertions.sh

# target="$1"

# if [ -z "$target" ]; then
#     echo "Target is required and must be either \"ios\" or \"android\"."
#     exit 1
# fi

assetDir="$(pwd)/$1"

if [ -z "$assetDir" ]; then
    echo "Asset directory argument is required"
    exit 1
fi

# Build Core Lib
spushd ../../fuse-js
    ./build.sh
    assertLastCall
spopd

# Build the test echo plugin
spushd ../../fuse-echo
    ./build.sh
    assertLastCall
spopd

spushd ../../fuse-test-app
    # Build the test app JS
    npm install
    assertLastCall

    node scripts/generateTestFile.js 
    assertLastCall

    # if [ "$target" == "android" ]; then
    mkdir -p $assetDir
    cp ./largeFile.txt "$assetDir"
    ASSET_DIR="$assetDir" npx webpack --mode development --config webpack.config.android.js
    assertLastCall
    # elif [ "$target" == "ios" ]; then
    #     cp ./largeFile.txt "$assetDir"
    #     ASSET_DIR="$assetDir" npx webpack --mode development --config webpack.config.ios.js
    #     assertLastCall
    # fi
spopd
