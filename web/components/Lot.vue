<template>
    <div class="app-lots">
        <div v-for="lot in lots">
            <p>{{ lot.name }}</p>
        </div>
    </div>
</template>

<script>
    export default {
        data: function () {
            return {
                lots: []
            }
        },
        beforeCreate: function () {
            this.lots = [
                {name: 'hello'},
                {name: 'hi'}
            ]
        }
    }

    function load() {
        return [{name: 'hello'}];
    }
</script>

<style scoped>

</style>